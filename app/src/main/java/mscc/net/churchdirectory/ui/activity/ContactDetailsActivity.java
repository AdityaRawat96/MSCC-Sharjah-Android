package mscc.net.churchdirectory.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mscc.net.churchdirectory.room.MainDatabase;
import mscc.net.churchdirectory.ui.fragment.NotificationDialogFragment;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.adapter.ContactNumbersAdapter;
import mscc.net.churchdirectory.adapter.MembersAdapter;
import mscc.net.churchdirectory.connection.ConnectionManager;
import mscc.net.churchdirectory.model.Directory;
import mscc.net.churchdirectory.model.ImageUpdate;
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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

@SuppressWarnings("FieldCanBeLocal")
public class ContactDetailsActivity extends AppCompatActivity {

    public static final String KEY_CONTACT = "key_contact";

    private static int RESULT_LOAD_IMAGE = 1;
    private static int REQUEST_LOAD_IMAGE = 2;

    private RecyclerView rvMembers;
    private List<Member> members;
    private MembersAdapter membersAdapter;
    private RecyclerView.LayoutManager membersLayoutManager;

    private RecyclerView rvContacts;
    private List<Contact> contacts;
    private ContactNumbersAdapter contactsAdapter;
    private RecyclerView.LayoutManager contactsLayoutManager;

    private NestedScrollView nestedScrollView;

    private TextView name;
    private TextView address;
    private TextView prayerGroup;
    private TextView permanentAddress;
    private TextView homeParish;
    private TextView emergencyContact;
    private TextView dom;

    private ImageView image;

    private RelativeLayout loading;
    private ProgressBar imageLoading;

    private FloatingActionButton fab;
    private ImageButton editImage;

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

    private int id;

    private android.support.v7.widget.Toolbar toolbar;

    private Observable<Response<Directory>> updateProfile(String id, String token, Directory.Family family) {
        return ConnectionManager
                .getInstance()
                .getClient()
                .updateProfile(id, token, family)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<ImageUpdate>> uploadImage(String id, String token, File file) {

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        return ConnectionManager
                .getInstance()
                .getClient()
                .updateImage(id, token, body, requestFile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);
        id = getIntent().getIntExtra(KEY_CONTACT, 0);

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

        family = familyDao.getFamily(id);

        loading.setVisibility(View.GONE);
        family.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::setUpContact, this::onDatabaseError);
    }

    private void onDatabaseError(Throwable throwable) {
        Log.e("Database Error", throwable.getLocalizedMessage());
    }

    @SuppressLint("CheckResult")
    private void setUpContact(Family family) {
        familyData = family;
        name.setText(family.getName());
        address.setText(family.getAddress());
        prayerGroup.setText((family.getPrayerGroup()));
        permanentAddress.setText(family.getPermanentAddress());
        homeParish.setText(family.getHomeParish());
        emergencyContact.setText(family.getEmergencyContact());
        dom.setText(family.getDom());
        fab.setVisibility(Objects.equals(String.valueOf(family.getId()), SessionManager.getInstance().getUserId(this)) ? View.VISIBLE : View.GONE);
        editImage.setVisibility(Objects.equals(String.valueOf(family.getId()), SessionManager.getInstance().getUserId(this)) ? View.VISIBLE : View.GONE);

        flowableContacts = contactDao.getContacts(family.getId());
        flowableMembers = memberDao.getMembers(family.getId());

        flowableContacts
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::setUpContacts, this::onDatabaseError);

        flowableMembers
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::setUpMembers, this::onDatabaseError);

        if (family.getImage() != null) {
            if (!family.getImage().isEmpty()) {
                Picasso.get().load(family.getImage())
                        .placeholder(R.drawable.ic_placeholder_background)
                        .error(R.drawable.ic_placeholder_background)
                        .into(image);
            }
        }

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, UpdateProfileActivity.class);
            intent.putExtra(ContactDetailsActivity.KEY_CONTACT, id);
            startActivity(intent);
        });

        editImage.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_LOAD_IMAGE);
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
            } else {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("requestCode", requestCode + "");
        Log.e("resultCode", resultCode + "");
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {

            Uri sourceUri = data.getData();
            Log.e("sourceUri", sourceUri.getPath());

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(sourceUri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Log.e("picture path", picturePath);

            Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped" + getExtension(picturePath)));
            Log.e("destinationUri", destinationUri.getPath());

            UCrop.Options cropOptions = new UCrop.Options();
            cropOptions.setCompressionFormat(Bitmap.CompressFormat.PNG);
            cropOptions.setCompressionFormat(Bitmap.CompressFormat.JPEG);

            UCrop.of(sourceUri, destinationUri)
                    .withAspectRatio(16, 9)
                    .withOptions(cropOptions)
                    .withMaxResultSize(600, 337)
                    .start(this, UCrop.REQUEST_CROP);
        }

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            Uri selectedImage = UCrop.getOutput(data);
            image.setImageBitmap(BitmapFactory.decodeFile(selectedImage.getPath()));
            File file = new File(selectedImage.getPath());
            imageLoading.setVisibility(View.VISIBLE);
            uploadImage(SessionManager.getInstance().getUserName(this), SessionManager.getInstance().getUserToken(this), file)
                    .subscribe(this::onImageUploaded, this::onImageUploadFailed);
        }
    }

    @SuppressLint("CheckResult")
    private void onImageUploaded(Response<ImageUpdate> response) {
        imageLoading.setVisibility(View.GONE);
        if (response.isSuccessful()) {
            familyData.setImage(response.body().getImageUrl());
            Observable.just(db)
                    .subscribeOn(Schedulers.io())
                    .subscribe(database -> database.getFamilyDao().update(familyData));
            toast(response.body().getMessage());
        } else {
            toast("Failed to upload image");
        }
    }

    private void onImageUploadFailed(Throwable throwable) {
        Log.e("imageUpload", throwable.getLocalizedMessage());
        toast("Failed to upload image");
        imageLoading.setVisibility(View.GONE);
    }

    private String getExtension(String f) {
        return f.substring(f.lastIndexOf("."));
    }

    private void toast(String d) {
        NotificationDialogFragment dialog = NotificationDialogFragment.newInstance(d);
        dialog.show(getFragmentManager(), "notification");
    }

    private void setUpMembers(List<Member> memberList) {
        membersData = memberList;
        this.members.clear();
        this.members.addAll(memberList);
        membersAdapter.notifyDataSetChanged();
    }

    private void setUpContacts(List<Contact> contactList) {
        contactsData = contactList;
        this.contacts.clear();
        this.contacts.addAll(contactList);
        contactsAdapter.notifyDataSetChanged();
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
        prayerGroup = findViewById(R.id.contact_prayer_group);
        permanentAddress = findViewById(R.id.contact_permanent_address);
        homeParish = findViewById(R.id.contact_home_parish);
        rvMembers = findViewById(R.id.rv_members);
        rvContacts = findViewById(R.id.rv_contacts);
        nestedScrollView = findViewById(R.id.nested_sv_contact);
        toolbar = findViewById(R.id.toolbar);
        loading = findViewById(R.id.loading);
        image = findViewById(R.id.family_image);
        fab = findViewById(R.id.fab);
        editImage = findViewById(R.id.image_edit);
        emergencyContact = findViewById(R.id.contact_emergency_contact);
        dom = findViewById(R.id.contact_dom);
        imageLoading = findViewById(R.id.image_loading);

        membersAdapter = new MembersAdapter(members, this, false, id);
        membersLayoutManager = new LinearLayoutManager(this);

        contactsAdapter = new ContactNumbersAdapter(contacts, this);
        contactsLayoutManager = new LinearLayoutManager(this);

        rvMembers.setHasFixedSize(true);
        rvMembers.setAdapter(membersAdapter);
        rvMembers.setLayoutManager(membersLayoutManager);
        rvMembers.setNestedScrollingEnabled(false);

        rvContacts.setHasFixedSize(true);
        rvContacts.setAdapter(contactsAdapter);
        rvContacts.setLayoutManager(contactsLayoutManager);
        rvContacts.setNestedScrollingEnabled(false);
    }

    public Directory.Family getFamily() {

        List<Directory.Member> mems = new ArrayList<>();
        List<Directory.Contact> cons = new ArrayList<>();

        for (Member m : membersData) {
            mems.add(new Directory.Member(m.getId(), m.getRelation(), m.getDob(), m.getBloodGroup(), m.getName()));
        }

        for (Contact c : contactsData) {
            cons.add(new Directory.Contact(c.getType(), c.getName(), c.getData()));
        }


        return new Directory.Family(id, familyData.getName(), familyData.getAddress(), familyData.getPrayerGroup(), familyData.getPermanentAddress()
                , familyData.getHomeParish(), familyData.getImage(), familyData.getEmergencyContact(), familyData.getDom(), mems, cons, familyData.getVisible());
    }
}
