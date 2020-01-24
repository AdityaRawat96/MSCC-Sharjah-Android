package danysam.net.churchdirectory.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import danysam.net.churchdirectory.R;
import danysam.net.churchdirectory.adapter.ContactsAdapter;
import danysam.net.churchdirectory.connection.ConnectionManager;
import danysam.net.churchdirectory.model.Directory;
import danysam.net.churchdirectory.room.MainDatabase;
import danysam.net.churchdirectory.room.dao.ContactDao;
import danysam.net.churchdirectory.room.dao.FamilyDao;
import danysam.net.churchdirectory.room.dao.MemberDao;
import danysam.net.churchdirectory.room.model.Contact;
import danysam.net.churchdirectory.room.model.Family;
import danysam.net.churchdirectory.room.model.Member;
import danysam.net.churchdirectory.session.SessionManager;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Dany on 02-01-2018.
 */

@SuppressWarnings("FieldCanBeLocal")
public class ContactsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RelativeLayout loading;
    private ContactsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Directory.Family> contacts = new ArrayList<>();
    private List<Directory.Family> allContacts = new ArrayList<>();
    private List<Member> allMembers = new ArrayList<>();
    private SessionManager session = SessionManager.getInstance();
    private MainDatabase db;
    private ContactDao contactDao;
    private FamilyDao familyDao;
    private MemberDao memberDao;
    private Flowable<List<Family>> flowableFamilies;

    private Observable<Response<Directory>> getFamilies(String id, String token, String lastUpdate) {
        return ConnectionManager.getInstance().getClient().getDirectory(id, token, lastUpdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        db = MainDatabase.getInstance(getActivity());
        contactDao = db.getContactDao();
        familyDao = db.getFamilyDao();
        memberDao = db.getMemberDao();

        recyclerView = view.findViewById(R.id.rv_contacts);
        loading = view.findViewById(R.id.loading);

        adapter = new ContactsAdapter(contacts, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        loading.setVisibility(View.GONE);
        getFamilies(
                session.getUserName(getActivity()),
                session.getUserToken(getActivity()),
                session.getLastUpdate(getActivity()))
                .subscribe(this::setFamilies, this::setError);

        flowableFamilies = familyDao.getFlowableFamilies();

        flowableFamilies
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onFamiliesUpdated, this::onDataError);

        memberDao.getFlowableMembers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMembersUpdated, this::onDataError);

        return view;
    }

    private void onMembersUpdated(List<Member> members) {
        allMembers.addAll(members);
    }

    private void onFamiliesUpdated(List<Family> families) {

        if (families.size() == 0) {
            refreshContactList();
        }

        contacts.clear();
        allContacts.clear();

        for (Family family : families) {

            List<Member> members = new ArrayList<>();
            /*memberDao.getMembers(family.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(members::addAll, this::onDataError);*/

            List<Contact> contactList = new ArrayList<>();
            //contactList = contactDao.getContacts(family.getId());

            allContacts.add(new Directory.Family(family.getId(),
                    family.getName(),
                    family.getAddress(),
                    family.getPrayerGroup(),
                    family.getPermanentAddress(),
                    family.getHomeParish(),
                    family.getImage(),
                    family.getEmergencyContact(),
                    family.getDom(), convertMembers(members), convertContacts(contactList),
                    family.getVisible()));
        }

        contacts.addAll(allContacts);
        adapter.notifyDataSetChanged();
    }

    private void refreshContactList() {
        getFamilies(
                session.getUserName(getActivity()),
                session.getUserToken(getActivity()),
                "")
                .subscribe(this::setFamilies, this::setError);
    }

    private List<Directory.Contact> convertContacts(List<Contact> contacts) {
        List<Directory.Contact> contactList = new ArrayList<>();
        for (Contact contact : contacts) {
            contactList.add(new Directory.Contact(contact.getType(), contact.getName(), contact.getData()));
        }

        return contactList;
    }

    private List<Directory.Member> convertMembers(List<Member> members) {
        List<Directory.Member> memberList = new ArrayList<>();
        for (Member m : members) {
            memberList.add(new Directory.Member(m.getId(), m.getRelation(), m.getDob(), m.getBloodGroup(), m.getName()));
        }

        return memberList;
    }


    private void onDataError(Throwable throwable) {
        Log.e("Database Update Error", throwable.getLocalizedMessage());
    }

    private void setError(Throwable throwable) {
        Toast.makeText(getActivity(), "Error\n" + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("ConstantConditions")
    private void setFamilies(Response<Directory> directoryResponse) {
        if (directoryResponse.isSuccessful()) {
            if (directoryResponse.body().getSuccess()) {
                responseToDatabase(directoryResponse.body().getResponse());
                loading.setVisibility(View.GONE);
                session.setLastUpdate(getActivity(), directoryResponse.body().getLastUpdate());
                if (!directoryResponse.body().getSuccess()) {
                    session.setLoggedOut(getActivity());
                }
            } else {
                Log.e("Directory_Response", directoryResponse.body().getError());
            }
        } else {
            session.setLoggedOut(getActivity());
        }
    }

    @SuppressLint("CheckResult")
    private void responseToDatabase(Directory.Response response) {
        List<Family> familyList = new ArrayList<>();
        List<Contact> contactList = new ArrayList<>();
        List<Member> memberList = new ArrayList<>();
        List<Integer> familyIds = new ArrayList<>();

        for (Directory.Family family : response.getFamily()) {
            familyList.add(new Family(family.getId(),
                    family.getName(),
                    family.getAddress(),
                    family.getPrayerGroup(),
                    family.getPermanentAddress(),
                    family.getHomeParish(),
                    family.getImage(),
                    family.getEmergencyContact(),
                    family.getDom(),
                    family.getVisible()));

            for (Directory.Contact contact : family.getContacts()) {
                contactList.add(new Contact(family.getId(), contact.getType(), contact.getName(), contact.getData()));
            }

            for (Directory.Member member : family.getMembers()) {
                memberList.add(new Member(member.getId(), family.getId(), member.getRelation(), member.getDob(), member.getBloodGroup(), member.getName()));
            }

            familyIds.add(family.getId());

        }

        Observable.just(db)
                .subscribeOn(Schedulers.io())
                .subscribe(mainDatabase -> {
                            for (Integer familyId : familyIds) {
                                Log.e("deleteMember", "familyId: " + familyId);
                                mainDatabase.getMemberDao().deleteMembers(familyId);
                                Log.e("deleteContact", "familyId: " + familyId);
                                mainDatabase.getContactDao().deleteContacts(familyId);
                            }
                            Log.e("insertMember", "memberSize: " + memberList.size());
                            mainDatabase.getMemberDao().insertAll(memberList);
                            Log.e("insertContact", "contactSize: " + memberList.size());
                            mainDatabase.getContactDao().insertAll(contactList);
                        },
                        err -> Log.e("RoomErrorMembers", err.getMessage()));

        Observable.just(db)
                .subscribeOn(Schedulers.io())
                .subscribe(mainDatabase -> mainDatabase.getFamilyDao().insertAll(familyList),
                        err -> Log.e("RoomErrorFamily", err.getMessage()));
    }


    public void searchContacts(String query) {
        if (query.length() > 0) {
            contacts.clear();
            for (Directory.Family f : allContacts) {

                boolean foundMember = false;

                if (f.getName().toLowerCase().contains(query.toLowerCase())) {
                    contacts.add(f);
                    foundMember = true;
                }

                for (Member m : minimizeMembers(f.getId())) {

                    if (m != null) {
                        if (m.getName().toLowerCase().contains(query.toLowerCase()) && !foundMember) {
                            contacts.add(f);
                            foundMember = true;
                        }
                    }
                }
            }
            adapter.notifyDataSetChanged();
        } else {
            contacts.clear();
            contacts.addAll(allContacts);
            adapter.notifyDataSetChanged();
        }
    }

    private List<Member> minimizeMembers(int id) {
        List<Member> memberList = new ArrayList<>();
        for (Member m : allMembers) {
            if (m.getFamilyId() == id) {
                memberList.add(m);
            }
        }

        return memberList;
    }

}
