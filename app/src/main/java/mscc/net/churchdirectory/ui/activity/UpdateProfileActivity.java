package mscc.net.churchdirectory.ui.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import mscc.net.churchdirectory.room.MainDatabase;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.adapter.MembersAdapter;
import mscc.net.churchdirectory.connection.ConnectionManager;
import mscc.net.churchdirectory.model.Directory;
import mscc.net.churchdirectory.room.dao.ContactDao;
import mscc.net.churchdirectory.room.dao.FamilyDao;
import mscc.net.churchdirectory.room.dao.MemberDao;
import mscc.net.churchdirectory.room.model.Contact;
import mscc.net.churchdirectory.room.model.Family;
import mscc.net.churchdirectory.room.model.Member;
import mscc.net.churchdirectory.session.SessionManager;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static mscc.net.churchdirectory.ui.activity.ContactDetailsActivity.KEY_CONTACT;

public class UpdateProfileActivity extends AppCompatActivity implements MembersAdapter.OnMemberClickListener {

    public static final String CONTACT_TYPE_MOBILE = "mobile";
    public static final String CONTACT_NAME_MOBILE = "Mobile";
    public static final String CONTACT_TYPE_LANDLINE = "landline";
    public static final String CONTACT_NAME_LANDLINE = "Residence";
    public static final String CONTACT_TYPE_HOME = "home";
    public static final String CONTACT_NAME_HOME = "Home";
    public static final String CONTACT_TYPE_EMAIL = "email";
    public static final String CONTACT_NAME_EMAIL = "Email";
    public static final String CONTACT_NAME_MOBILE_SECONDARY = "Mobile Secondary";
    public static final String REGEX_DATE = "[0-3]?[0-9] [A-Za-z]{3}";
    private RecyclerView rvMembers;
    private List<Member> members;
    private MembersAdapter membersAdapter;
    private RecyclerView.LayoutManager membersLayoutManager;

    private List<Contact> contacts;

    private NestedScrollView nestedScrollView;

    private EditText name;
    private EditText diocese;
    private EditText address;
    private Spinner prayerGroup;
    private EditText permanentAddress;
    private EditText homeParish;
    private EditText emergencyContact;
    private EditText dob;
    private EditText dom;

    private ImageView image;

    private RelativeLayout loading;

    private FloatingActionButton fab;

    private MainDatabase db;
    private ContactDao contactDao;
    private FamilyDao familyDao;
    private MemberDao memberDao;
    private Flowable<Family> family;
    private Flowable<List<Contact>> flowableContacts;
    private Flowable<List<Member>> flowableMembers;

    private Family familyData;
    private List<Contact> contactsData;
    private List<Member> membersData;
    private List<Member> membersDataBackup;

    private EditText etContactMobile;
    private EditText etContactLandline;
    private EditText etContactHome;
    private EditText etContactEmail;
    private EditText etContactMobileSecondary;
    private Button addMember;
    private Boolean isDobValid = false;
    private Boolean isDomValid = false;
    private Boolean isPrayerGroupValid = false;

    private int id;

    private Boolean isMemberModified = false;

    private Toolbar toolbar;

    private List<String> prayerGroups = new ArrayList<>();

    private Observable<Response<Directory>> updateProfile(String id, String token, Directory.Family family) {
        return ConnectionManager
                .getInstance()
                .getClient()
                .updateProfile(id, token, family)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        id = getIntent().getIntExtra(KEY_CONTACT, 0);

        initViews();
        initPrayerGroups();

        ArrayAdapter<String> prayerGroupAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, prayerGroups);
//        prayerGroup.setThreshold(0);
        prayerGroupAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prayerGroup.setAdapter(prayerGroupAdapter);

        db = MainDatabase.getInstance(this);
        contactDao = db.getContactDao();
        familyDao = db.getFamilyDao();
        memberDao = db.getMemberDao();

        members = new ArrayList<>();
        contacts = new ArrayList<>();

        initViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        flowableContacts = contactDao.getContacts(id);
        flowableMembers = memberDao.getMembers(id);

        flowableContacts
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::setUpContacts, this::onDatabaseError);

        flowableMembers
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::setUpMembers, this::onDatabaseError);

        family = familyDao.getFamily(id);

        loading.setVisibility(View.GONE);
        family.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::setUpContact, this::onDatabaseError);

        addMember.setOnClickListener(v -> {
            isMemberModified = true;
            Intent i = new Intent(this, AddMemberActivity.class);
            i.putExtra(AddMemberActivity.EXTRA_FAMILY_ID, id);
            startActivity(i);
        });

        dob.setOnFocusChangeListener((v, hasFocus) -> showDatePickerDOB());
        dob.setOnClickListener(v -> showDatePickerDOB());

        dob.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isDobValid = Pattern.matches(REGEX_DATE, s) || s.length() == 0;
                if (isDobValid) {
                    dob.setError(null);
                } else {
                    dob.setError("Pattern doesn't match");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dom.setOnFocusChangeListener((v, hasFocus) -> showDatePickerDOM());
        dom.setOnClickListener(v -> showDatePickerDOM());

        dom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isDomValid = Pattern.matches(REGEX_DATE, s) || s.length() == 0;
                if (isDomValid) {
                    dom.setError(null);
                } else {
                    dom.setError("Pattern doesn't match");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fab.setOnClickListener(v -> {
            isMemberModified = false;
            updateProfile(SessionManager.getInstance().getUserName(this),
                    SessionManager.getInstance().getUserToken(this), getFamily())
                    .subscribe(r -> showDialog(r.body().getMessage()), e -> showDialog(e.getLocalizedMessage()));
        });
    }

    private Boolean validatePrayerGroup(String s) {
        return (s.length() > 0 && prayerGroups.contains(s));
    }

    private void initPrayerGroups() {
        prayerGroups.add("- SELECT PRAYER GROUP -");
        prayerGroups.add("Our Lady of Arabia Prayer Group - Umm Al Quwain");
        prayerGroups.add("St. Peter Prayer Group - Nabba Buteena");
        prayerGroups.add("St. Joseph Prayer - Rolla");
        prayerGroups.add("St. George Prayer Group - J & P");
        prayerGroups.add("St. Johns Prayer Group  - Al Khan");
        prayerGroups.add("St. Jude Prayer Group - GPO");
        prayerGroups.add("St. Thomas Prayer Group - Al Nadha");
        prayerGroups.add("St. Alphonsa Prayer Group - National Paint");
        prayerGroups.add("Mar Ivanios Prayer Group - Ajman");
        prayerGroups.add("St. Francis of Assisi Prayer - Church Area");
        prayerGroups.add("St. Theresa Prayer Group - Abu Shagara");
        prayerGroups.add("Mar Gregorios Prayer Group - Ajman");
    }

    private void onDatabaseError(Throwable throwable) {
        Log.e("Database Error", throwable.getLocalizedMessage());
    }

    private int getGroupId(String pg) {
        int pId = 0;

        for (int i = 0; i < prayerGroups.size(); i++) {
            if (pg.equals(prayerGroups.get(i))) {
                pId = i;
            }
        }

        return pId;
    }

    @SuppressLint({"CheckResult", "RestrictedApi"})
    private void setUpContact(Family family) {
        familyData = family;
        name.setText(family.getName());
        diocese.setText(family.getDiocese());
        address.setText(family.getAddress());
        permanentAddress.setText(family.getPermanentAddress());
        homeParish.setText(family.getHomeParish());
        emergencyContact.setText(family.getEmergencyContact());
        isDobValid = Pattern.matches(REGEX_DATE, family.getDob());
        isDomValid = Pattern.matches(REGEX_DATE, family.getDom());
        dob.setText(family.getDob());
        if (isDobValid) {
            dob.setError(null);
        } else {
            dob.setError("Pattern is DD MMM");
        }
        dom.setText(family.getDom());
        if (isDomValid) {
            dom.setError(null);
        } else {
            dom.setError("Pattern is DD MMM");
        }

//        prayerGroup.setText((family.getPrayerGroup()));
        prayerGroup.setSelection(getGroupId(family.getPrayerGroup()));

        fab.setVisibility(Objects.equals(String.valueOf(family.getId()), SessionManager.getInstance().getUserId(this)) ? View.VISIBLE : View.GONE);

        if (family.getImage() != null) {
            if (!family.getImage().isEmpty()) {
                Picasso.get().load(family.getImage())
                        .placeholder(R.drawable.ic_placeholder_background)
                        .error(R.drawable.ic_placeholder_background)
                        .into(image);
            }
        }
    }

    @SuppressLint("CheckResult")
    @Override
    public void onBackPressed() {
        if (isMemberModified) {
            Observable.just(db)
                    .subscribeOn(Schedulers.io())
                    .subscribe(database -> {
                        database.getMemberDao().deleteMembers(id);
                        database.getMemberDao().insertAll(membersDataBackup);
                    });
        }
        super.onBackPressed();
    }

    private void showDatePickerDOB() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("DefaultLocale") DatePickerDialog dpd = new DatePickerDialog(this,
                (datePicker, year, month, day) -> {
                    dob.setText(String.format("%d %s", day, getMonth(month+1)));
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    private void showDatePickerDOM() {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("DefaultLocale") DatePickerDialog dpd = new DatePickerDialog(this,
                (datePicker, year, month, day) -> {
                    dom.setText(String.format("%d %s", day, getMonth(month+1)));
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dpd.show();
    }

    private String getMonth(int month) {
        String monthString;
        switch (month) {
            case 1:
                monthString = "Jan";
                break;
            case 2:
                monthString = "Feb";
                break;
            case 3:
                monthString = "Mar";
                break;
            case 4:
                monthString = "Apr";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "Jun";
                break;
            case 7:
                monthString = "Jul";
                break;
            case 8:
                monthString = "Aug";
                break;
            case 9:
                monthString = "Sep";
                break;
            case 10:
                monthString = "Oct";
                break;
            case 11:
                monthString = "Nov";
                break;
            case 12:
                monthString = "Dec";
                break;
            default:
                monthString = "Jan";
                break;
        }
        return monthString;
    }

    private void showDialog(String d) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(d);
        builder.setPositiveButton("Ok", (dialog, id) -> finish());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setUpMembers(List<Member> memberList) {
        membersData = memberList;
        if (!isMemberModified) {
            membersDataBackup = memberList;
        }
        this.members.clear();
        this.members.addAll(memberList);
        membersAdapter.notifyDataSetChanged();
    }

    private void setUpContacts(List<Contact> contactList) {
        contactsData = contactList;
        this.contacts.clear();
        this.contacts.addAll(contactList);

        int mobileCount = 0;
        for (Contact c : contactList) {

            if (c.getType().equals("mobile")) {
                if (mobileCount == 0) {
                    mobileCount++;

                    etContactMobile.setText(c.getData());
                } else {
                    etContactMobileSecondary.setText(c.getData());
                }
            }

            if (c.getType().equals("landline")) {
                etContactLandline.setText(c.getData());
            }

            if (c.getType().equals("home")) {
                etContactHome.setText(c.getData());
            }

            if (c.getType().equals("email")) {
                etContactEmail.setText(c.getData());
            }

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void initViews() {
        name = findViewById(R.id.contact_name);
        address = findViewById(R.id.contact_address);
        diocese = findViewById(R.id.contact_diocese);
        prayerGroup = findViewById(R.id.contact_prayer_group);
        permanentAddress = findViewById(R.id.contact_permanent_address);
        homeParish = findViewById(R.id.contact_home_parish);
        rvMembers = findViewById(R.id.rv_members);
        nestedScrollView = findViewById(R.id.nested_sv_contact);
        toolbar = findViewById(R.id.toolbar);
        loading = findViewById(R.id.loading);
        image = findViewById(R.id.family_image);
        fab = findViewById(R.id.fab);
        emergencyContact = findViewById(R.id.contact_emergency_contact);
        dob = findViewById(R.id.contact_dob);
        dom = findViewById(R.id.contact_dom);
        etContactMobile = findViewById(R.id.contact_mobile);
        etContactLandline = findViewById(R.id.contact_landline);
        etContactHome = findViewById(R.id.contact_home);
        etContactEmail = findViewById(R.id.contact_email);
        etContactMobileSecondary = findViewById(R.id.contact_mobile_secondary);
        addMember = findViewById(R.id.button_add_member);

        membersAdapter = new MembersAdapter(members, this, true, id, this);
        membersLayoutManager = new LinearLayoutManager(this);

        rvMembers.setHasFixedSize(true);
        rvMembers.setAdapter(membersAdapter);
        rvMembers.setLayoutManager(membersLayoutManager);
        rvMembers.setNestedScrollingEnabled(false);
    }

    public Directory.Family getFamily() {

        List<Directory.Member> mems = new ArrayList<>();
        List<Directory.Contact> cons = new ArrayList<>();

        if (membersData != null) {
            for (Member m : membersData) {
                mems.add(new Directory.Member(m.getId(), m.getRelation(), m.getDob(), m.getBloodGroup(), m.getName()));
            }
        }

        if (etContactMobile.getText() != null && etContactMobile.getText().toString().length() > 0) {
            cons.add(new Directory.Contact(CONTACT_TYPE_MOBILE, CONTACT_NAME_MOBILE, etContactMobile.getText().toString()));
        }

        if (etContactLandline.getText() != null && etContactLandline.getText().toString().length() > 0) {
            cons.add(new Directory.Contact(CONTACT_TYPE_LANDLINE, CONTACT_NAME_LANDLINE, etContactLandline.getText().toString()));
        }

        if (etContactHome.getText() != null && etContactHome.getText().toString().length() > 0) {
            cons.add(new Directory.Contact(CONTACT_TYPE_HOME, CONTACT_NAME_HOME, etContactHome.getText().toString()));
        }

        if (etContactEmail.getText() != null && etContactEmail.getText().toString().length() > 0) {
            cons.add(new Directory.Contact(CONTACT_TYPE_EMAIL, CONTACT_NAME_EMAIL, etContactEmail.getText().toString()));
        }

        if (etContactMobileSecondary.getText() != null && etContactMobileSecondary.getText().toString().length() > 0) {
            cons.add(new Directory.Contact(CONTACT_TYPE_MOBILE, CONTACT_NAME_MOBILE_SECONDARY, etContactMobileSecondary.getText().toString()));
        }

        return new Directory.Family(id, name.getText().toString(), diocese.getText().toString(), address.getText().toString(), prayerGroup.getSelectedItem().toString(), permanentAddress.getText().toString()
                , homeParish.getText().toString(), familyData.getImage(), emergencyContact.getText().toString(), dob.getText().toString(), dom.getText().toString(), mems, cons, true);
    }

    @Override
    public void onMemberClicked() {
        isMemberModified = true;
    }
}
