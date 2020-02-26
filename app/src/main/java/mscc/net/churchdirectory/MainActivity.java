package mscc.net.churchdirectory;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.perf.FirebasePerformance;
import com.google.firebase.perf.metrics.Trace;
import com.google.gson.Gson;
import mscc.net.churchdirectory.room.MainDatabase;
import mscc.net.churchdirectory.ui.activity.AdvertisementActivity;
import mscc.net.churchdirectory.ui.activity.ArticlesActivity;
import mscc.net.churchdirectory.ui.activity.CalendarActivity;
import mscc.net.churchdirectory.ui.activity.CommitteeActivity;
import mscc.net.churchdirectory.ui.activity.ContactDetailsActivity;
import mscc.net.churchdirectory.ui.activity.ContactsActivity;
import mscc.net.churchdirectory.ui.activity.DownloadActivity;
import mscc.net.churchdirectory.ui.activity.HierarchyActivity;
import mscc.net.churchdirectory.ui.activity.HttpHandler;
import mscc.net.churchdirectory.ui.activity.NotificationActivity;
import mscc.net.churchdirectory.ui.activity.PiousAssociationsActivity;
import mscc.net.churchdirectory.ui.activity.PushNotificationActivity;
import mscc.net.churchdirectory.ui.activity.WebViewActivity;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.pushnotifications.PushNotifications;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import mscc.net.churchdirectory.connection.ConnectionManager;
import mscc.net.churchdirectory.model.Directory;
import mscc.net.churchdirectory.model.PushNotification;
import mscc.net.churchdirectory.model.PushToken;
import mscc.net.churchdirectory.room.model.Contact;
import mscc.net.churchdirectory.room.model.Family;
import mscc.net.churchdirectory.room.model.Member;
import mscc.net.churchdirectory.service.MyFirebaseMessagingService;
import mscc.net.churchdirectory.session.SessionManager;
import mscc.net.churchdirectory.model.Advertisement;
import mscc.net.churchdirectory.database.AdvertisementDatabase;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public static final String PUSHER_CHANNEL_GENERAL = "general";
    public static final String PUSHER_EVENT_NOTIFICATION = "notification";
    public static final String PUSHER_CLUSTER = "ap2";
    public static final String PUSHER_API_KEY = "c3172b4796264841156e";
    public static final String ANALYTICS_EVENT_SESSION = "session";
    public static final String ANALYTICS_TAG_IS_LOGGED_IN = "isLoggedIn";
    public static final String ANALYTICS_TAG_USER_ID = "userId";
    public static final String PUSH_NOTIFICATION_INSTANCE_ID = "c02d32e6-82da-4487-9531-17953ec4fc87";
    public static final String PUSH_NOTIFICATION_INTEREST_GENERAL = "general";
    public static final String PUSH_NOTIFICATION_INTEREST_NOTIFICATION = "notification";

    private static String url = "http://msccsharjah.com/api/ads/";
    private static String urlNews = "http://msccsharjah.com/api/notification/";
    private String TAG = MainActivity.class.getSimpleName();

    private FirebaseAnalytics mFirebaseAnalytics;
    private Trace myTrace;

    private DrawerLayout drawerLayout;
    private AppBarLayout appBarLayout;
    private net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout collapsingToolbarLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private Button about;
    private Button directory;
    private Button committee;
    private Button news;
    private Button articles;
    private Button pious;
    private Button calender;
    private Button downloads;
    private Button contact;
    private ViewPager adView;
    private ImageView adImage;
    private TextView marqueeText;
    private List<mscc.net.churchdirectory.room.model.Notification> newsList;
    private String newsString = "";

    private MainDatabase db;

    private Activity activity;

    private SessionManager session = SessionManager.getInstance();

    private Gson gson = new Gson();

    private Random random = new Random();
    private int imageNumber = 0;

    private List<Advertisement> adList = new ArrayList<>();
    private List<Advertisement> adListUpdated = new ArrayList<>();
    AdvertisementDatabase advertisementDatabase = new AdvertisementDatabase(this);

    private Observable<Response<PushToken>> sendToken(String userId, String token, String pushToken) {
        return ConnectionManager
                .getInstance()
                .getClient()
                .sendToken(userId, token, pushToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Response<Directory>> getFamilies(String id, String token, String lastUpdate) {
        return ConnectionManager.getInstance().getClient().getDirectory(id, token, lastUpdate)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @SuppressLint({"ClickableViewAccessibility", "CheckResult"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        adList = advertisementDatabase.getAllAdvertisements();

        myTrace = FirebasePerformance.getInstance().newTrace("test_trace");
        myTrace.start();

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                // TODO: 20-01-2018 Add handle for extra data send from the server
                Log.e(MyFirebaseMessagingService.TAG, "Key: " + key + " Value: " + value);
            }
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PushNotifications.start(getApplicationContext(), PUSH_NOTIFICATION_INSTANCE_ID);
        PushNotifications.subscribe(PUSH_NOTIFICATION_INTEREST_GENERAL);
        if (SessionManager.getInstance().isLoggedIn(this)) {
            Log.e("PushNotification", "Subscribing to" + SessionManager.getInstance().getUserId(this));
            PushNotifications.subscribe(SessionManager.getInstance().getUserId(this));
        }

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().get(MyFirebaseMessagingService.EXTRA_IS_ARTICLE) != null) {
                if (getIntent().getExtras().get(MyFirebaseMessagingService.EXTRA_IS_ARTICLE).equals("true")) {
                    startActivity(new Intent(this, ArticlesActivity.class));
                }
            }

            if (getIntent().getExtras().get(MyFirebaseMessagingService.EXTRA_IS_NOTIFICATION) != null) {
                if (getIntent().getExtras().get(MyFirebaseMessagingService.EXTRA_IS_NOTIFICATION).equals("true")) {
                    startActivity(new Intent(this, NotificationActivity.class));
                }
            }

            if (getIntent().getExtras().get(MyFirebaseMessagingService.EXTRA_IS_BIRTHDAY) != null) {
                if (getIntent().getExtras().get(MyFirebaseMessagingService.EXTRA_IS_BIRTHDAY).equals("true")) {
                    PushNotification pushNotification =
                            new PushNotification(getIntent().getExtras().getString(MyFirebaseMessagingService.EXTRA_TITLE),
                                    getIntent().getExtras().getString(MyFirebaseMessagingService.EXTRA_MESSAGE),
                                    getIntent().getExtras().getString(MyFirebaseMessagingService.EXTRA_IMAGE),
                                    true, false, false, false);
                    Intent intent = new Intent(this, PushNotificationActivity.class);
                    intent.putExtra(PushNotificationActivity.TAG_PUSH, pushNotification);
                    startActivity(intent);
                }
            }

            if (getIntent().getExtras().get(MyFirebaseMessagingService.EXTRA_IS_ANNIVERSARY) != null) {
                if (getIntent().getExtras().get(MyFirebaseMessagingService.EXTRA_IS_ANNIVERSARY).equals("true")) {
                    PushNotification pushNotification =
                            new PushNotification(getIntent().getExtras().getString(MyFirebaseMessagingService.EXTRA_TITLE),
                                    getIntent().getExtras().getString(MyFirebaseMessagingService.EXTRA_MESSAGE),
                                    getIntent().getExtras().getString(MyFirebaseMessagingService.EXTRA_IMAGE),
                                    false, true, false, false);
                    Intent intent = new Intent(this, PushNotificationActivity.class);
                    intent.putExtra(PushNotificationActivity.TAG_PUSH, pushNotification);
                    startActivity(intent);
                }
            }
        }

        // TODO: 20-01-2018 Send token to backend
        String token = FirebaseInstanceId.getInstance().getToken();
        sendToken(SessionManager.getInstance().getUserName(this), SessionManager.getInstance().getUserToken(this), token)
                .subscribe(this::onTokenSent, this::onTokenSendError);

        PushNotifications.start(getApplicationContext(), PUSH_NOTIFICATION_INSTANCE_ID);
        PushNotifications.subscribe(PUSH_NOTIFICATION_INTEREST_GENERAL);

        db = MainDatabase.getInstance(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        drawerLayout = findViewById(R.id.main_drawer_layout);
        navigationView = findViewById(R.id.main_navigation_view);
        toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.main_collapsing_toolbar);
        appBarLayout = findViewById(R.id.main_app_bar);

        about = findViewById(R.id.button_main_about);
        directory = findViewById(R.id.button_main_directory);
        committee = findViewById(R.id.button_main_committee);
        news = findViewById(R.id.button_main_news);
        articles = findViewById(R.id.button_main_articles);
        pious = findViewById(R.id.button_main_pious);
        calender = findViewById(R.id.button_main_calender);
        downloads = findViewById(R.id.button_main_downloads);
        contact = findViewById(R.id.button_main_contact);
        adImage = findViewById(R.id.advertisementImageView);
        marqueeText = findViewById(R.id.MarqueeText);

        about.setOnClickListener(v -> navigateAbout());
        directory.setOnClickListener(v -> navigateDirectory());
        articles.setOnClickListener(v -> navigateArticles());
        news.setOnClickListener(v -> navigateNews());
        committee.setOnClickListener(v -> navigateCommittee());
        calender.setOnClickListener(v -> navigateCalendar());
        contact.setOnClickListener(v -> navigateContacts());
        pious.setOnClickListener(v -> navigatePious());
        downloads.setOnClickListener(v -> navigateDownloads());
        marqueeText.setOnClickListener(v -> navigateNews());

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adList = new ArrayList<>();

        newsList = new ArrayList<>();

        // Disable ViewPager Scrolling
        // adView.setOnTouchListener((v, event) -> true);

        PusherOptions options = new PusherOptions();
        options.setCluster(PUSHER_CLUSTER);
        Pusher pusher = new Pusher(PUSHER_API_KEY, options);

        String channelName = SessionManager.getInstance().getUserId(this);
        if (channelName.length() == 0) {
            channelName = PUSHER_CHANNEL_GENERAL;
        }

        Channel channel = pusher.subscribe(channelName);

        activity = MainActivity.this;

        channel.bind(PUSHER_EVENT_NOTIFICATION, (cName, eventName, data) -> activity.runOnUiThread(() -> {
            PushNotification notification = gson.fromJson(data, PushNotification.class);
            new generateBubbleNotification(activity, notification)
                    .execute();
        }));

        pusher.connect();

        drawerLayout.setDrawerListener(toggle);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();
        collapsingToolbarLayout.setExpandedTitleColor(ResourcesCompat.getColor(getResources(), R.color.white, getTheme()));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ResourcesCompat.getColor(getResources(), R.color.white, getTheme()));

        collapsingToolbarLayout.setTitle(" ");

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
        if(haveNetworkConnection() == true){
            new GetAdvertisements().execute();
            new GetNews().execute();
        }else{
            initAdvertisements();
            new GetNewsListDb().execute();
        }

        adImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, AdvertisementActivity.class);
                myIntent.putExtra("name", adList.get(imageNumber-1).getAdvertisementName());
                myIntent.putExtra("description", adList.get(imageNumber-1).getAdvertisementDescription());
                myIntent.putExtra("image", adList.get(imageNumber-1).getAdvertisementImageFull());
                myIntent.putExtra("website", adList.get(imageNumber-1).getAdvertisementWebsite());
                myIntent.putExtra("phone", adList.get(imageNumber-1).getAdvertisementPhone());
                myIntent.putExtra("timestamp", adList.get(imageNumber-1).getAdvertisementTimestamp());
                MainActivity.this.startActivity(myIntent);
            }
        });

        myTrace.stop();

        if (!SessionManager.getInstance().isLoggedIn(this)) {
            navigationView = (NavigationView) findViewById(R.id.main_navigation_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.drawer_menu_logout).setVisible(false);
        }

        if (!SessionManager.getInstance().getIsMember(this)) {
            navigationView = (NavigationView) findViewById(R.id.main_navigation_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.drawer_menu_profile).setVisible(false);
        }

    }




    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void initAdvertisements() {
        adList.clear();
        adList = advertisementDatabase.getAllAdvertisements();
        initAdScroll();
    }

    private void initNews() {
        for(int i = 0; i < newsList.size(); i++){
            newsString = newsString + newsList.get(i).getTitle()+ " | ";
        }
        if(newsString == ""){
            marqueeText.setVisibility(View.INVISIBLE);
        }else{
            marqueeText.setVisibility(View.VISIBLE);
            marqueeText.setText(newsString);
            marqueeText.setSelected(true);
        }
    }



    private void initAdScroll() {
        final android.os.Handler handler = new android.os.Handler();
        final Runnable run = () -> {
            if (adList.size() > 0){

                Date date= new Date();
                long dfd = date.getTime();
                int position = getNextAd();

                final Animation anim_out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
                final Animation anim_in  = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
                anim_out.setAnimationListener(new Animation.AnimationListener()
                {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation)
                    {
                        Uri imageURI = Uri.parse("http://msccsharjah.com/imagesAds/banner/" + adList.get(position).getAdvertisementImageBanner() + "?" + adList.get(position).getAdvertisementTimestamp());
                        Picasso.get().load(imageURI).into(adImage);
                        anim_in.setAnimationListener(new Animation.AnimationListener() {
                            @Override public void onAnimationStart(Animation animation) {}
                            @Override public void onAnimationRepeat(Animation animation) {}
                            @Override public void onAnimationEnd(Animation animation) {}
                        });
                        adImage.startAnimation(anim_in);
                    }
                });
                adImage.startAnimation(anim_out);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(run);
            }
        }, 100, 5000);
    }

    private int getNextAd() {
        int pos = imageNumber;
        if(pos % adList.size() == 0){
            imageNumber = 0;
        }
        imageNumber++;
        return pos % adList.size();
    }

    private class GetNewsListDb extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            newsList = db.getNotificationDao().getNews();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            initNews();
            super.onPostExecute(result);
        }
    }

    private class GetNews extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            db.getNotificationDao().nukeTable();

            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(urlNews);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONObject notificationObj = jsonObj.getJSONObject("response");

                    JSONArray notificationArray = notificationObj.getJSONArray("notifications");

                    // looping through All Contacts
                    for (int i = 0; i < notificationArray.length(); i++) {
                        JSONObject m = notificationArray.getJSONObject(i);

                        mscc.net.churchdirectory.room.model.Notification notification = new mscc.net.churchdirectory.room.model.Notification();

                        notification.setId(Integer.parseInt(m.getString("id")));
                        notification.setTitle(m.getString("title"));
                        notification.setMessage(m.getString("message"));
                        notification.setImage(m.getString("image"));
                        notification.setLink(m.getString("link"));
                        notification.setDate(m.getString("date"));

                        db.getNotificationDao().insert(notification);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "Couldn't get json from server.");
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            new GetNewsListDb().execute();
        }
    }

    private class GetAdvertisements extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            advertisementDatabase.deleteAllRecords();
        }

        @Override
        protected Void doInBackground(Void... arg0) {



            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray advertisementArray = jsonObj.getJSONArray("response");

                    // looping through All Contacts
                    for (int i = 0; i < advertisementArray.length(); i++) {
                        JSONObject m = advertisementArray.getJSONObject(i);

                        Advertisement advertisement = new Advertisement();

                        advertisement.setAdvertisementId(m.getString("id"));
                        advertisement.setAdvertisementName(m.getString("name"));
                        advertisement.setAdvertisementDescription(m.getString("description"));
                        advertisement.setAdvertisementImageBanner(m.getString("image_small"));
                        advertisement.setAdvertisementImageFull(m.getString("image_large"));
                        advertisement.setAdvertisementWebsite(m.getString("website"));
                        advertisement.setAdvertisementPhone(m.getString("phone"));
                        advertisement.setAdvertisementTimestamp(m.getString("adTimestamp"));

                        advertisementDatabase.addItem(advertisement);
                        adListUpdated.add(advertisement);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "Json parsing error: " + e.getMessage());
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "Couldn't get json from server.");
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            initAdvertisements();
        }

    }

    @SuppressLint("CheckResult")
    @Override
    protected void onResume() {
        super.onResume();
        getFamilies(
                SessionManager.getInstance().getUserName(this),
                SessionManager.getInstance().getUserToken(this),
                SessionManager.getInstance().getLastUpdate(this))
                .subscribe(this::setFamilies, this::setError);
    }

    private void setFamilies(Response<Directory> directoryResponse) {
        if (directoryResponse.isSuccessful()) {
            if (directoryResponse.body().getSuccess()) {
                responseToDatabase(directoryResponse.body().getResponse());
                SessionManager.getInstance().setLastUpdate(this, directoryResponse.body().getLastUpdate());
                if (!directoryResponse.body().getSuccess()) {
                    session.setLoggedOut(this);
                }
            } else {
                Log.e("Directory_Response", directoryResponse.body().getError());
            }
        } else {
            session.setLoggedOut(this);
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

            familyIds.add(family.getId());

            for (Directory.Contact contact : family.getContacts()) {
                contactList.add(new Contact(family.getId(), contact.getType(), contact.getName(), contact.getData()));
            }

            for (Directory.Member member : family.getMembers()) {
                memberList.add(new Member(member.getId(), family.getId(), member.getRelation(), member.getDob(), member.getBloodGroup(), member.getName()));
            }
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

    private void onTokenSendError(Throwable throwable) {
        Log.e("pushToken", throwable.getLocalizedMessage());
    }

    private void onTokenSent(Response<PushToken> pushTokenResponse) {
        if (pushTokenResponse.isSuccessful()) {
            assert pushTokenResponse.body() != null;
            Log.e("pushToken", pushTokenResponse.body().getMessage());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle params = new Bundle();
        params.putBoolean(ANALYTICS_TAG_IS_LOGGED_IN, SessionManager.getInstance().isLoggedIn(this));
        params.putString(ANALYTICS_TAG_USER_ID, SessionManager.getInstance().getUserId(this));
        mFirebaseAnalytics.logEvent(ANALYTICS_EVENT_SESSION, params);

        mFirebaseAnalytics.setUserId(SessionManager.getInstance().getUserId(this));
        mFirebaseAnalytics.setUserProperty(ANALYTICS_EVENT_SESSION, SessionManager.getInstance().isLoggedIn(this) ? "loggedIn" : "loggedOut");
    }

    private void setError(Throwable throwable) {
        Log.e("RX Network Error", throwable.getLocalizedMessage());
    }

    private int randomize(int bound) {
        return random.nextInt(bound);
    }

    private void navigateDownloads() {
        startActivity(new Intent(this, DownloadActivity.class));
    }

    private void navigatePious() {
        startActivity(new Intent(this, PiousAssociationsActivity.class));
    }

    private void navigateContacts() {
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("urlString", "http://msccsharjah.com/Pages/ContactUs.php?");
        startActivity(intent);
    }

    private void navigateCalendar() {
        startActivity(new Intent(this, CalendarActivity.class));
    }

    private void navigateCommittee() {
        startActivity(new Intent(this, CommitteeActivity.class));
    }

    private void navigateNews() {
        startActivity(new Intent(this, NotificationActivity.class));
    }

    private void navigateArticles() {
        startActivity(new Intent(this, ArticlesActivity.class));
    }

    private void navigateDirectory() {
        if (!SessionManager.getInstance().isLoggedIn(this)) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            if (!SessionManager.getInstance().getIsMember(this)) {
                Toast.makeText(this, "Feature not available for Guest users!", Toast.LENGTH_SHORT).show();
            }else{
                startActivity(new Intent(this, ContactsActivity.class));
            }
        }
    }

    private void navigateHierarchy() {
        startActivity(new Intent(this, HierarchyActivity.class));
    }

    private void navigateCredits() {
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("urlString", "http://msccsharjah.com/Pages/Credits.php?");
        startActivity(intent);
    }

    private void navigateLinks() {
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("urlString", "http://msccsharjah.com/Pages/Links.php?");
        startActivity(intent);
    }

    private void navigateTiming() {
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("urlString", "http://msccsharjah.com/Pages/HolyMassTiming.php?");
        startActivity(intent);
    }

    private void logout() {
        SessionManager.getInstance().setLoggedOut(this);
        if (!SessionManager.getInstance().isLoggedIn(this)) {
            navigationView = (NavigationView) findViewById(R.id.main_navigation_view);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.drawer_menu_logout).setVisible(false);
        }
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("SplashRedirect", true);
        startActivity(i);
        finish();
        Toast.makeText(activity, "You've been logged out", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        switch (item.getItemId()) {
            case R.id.drawer_menu_about:
                navigateAbout();
                break;

            case R.id.drawer_menu_directory:
                navigateDirectory();
                break;

            case R.id.drawer_menu_news:
                navigateNews();
                break;

            case R.id.drawer_menu_committee:
                navigateCommittee();
                break;

            case R.id.drawer_menu_articles:
                navigateArticles();
                break;

            case R.id.drawer_menu_associations:
                navigatePious();
                break;

            case R.id.drawer_menu_calender:
                navigateCalendar();
                break;

            case R.id.drawer_menu_download:
                navigateDownloads();
                break;

            case R.id.drawer_menu_hierarchy:
                navigateHierarchy();
                break;

            case R.id.drawer_menu_timing:
                navigateTiming();
                break;

            case R.id.drawer_menu_links:
                navigateLinks();
                break;

            case R.id.drawer_menu_contact_us:
                navigateContacts();
                break;

            case R.id.drawer_menu_credits:
                navigateCredits();
                break;

            case R.id.drawer_menu_profile:
                navigateUserProfile();
                break;

            case R.id.drawer_menu_logout:
                logout();
                break;
        }

        drawerLayout.closeDrawer(Gravity.START, true);

        return true;
    }

    private void navigateUserProfile() {
        if (SessionManager.getInstance().isLoggedIn(this)) {
            if (SessionManager.getInstance().getIsMember(this)) {
                Intent i = new Intent(this, ContactDetailsActivity.class);
                i.putExtra(ContactDetailsActivity.KEY_CONTACT, Integer.valueOf(SessionManager.getInstance().getUserId(this)));
                startActivity(i);
            } else {
                Toast.makeText(this, "You are not logged in as a member", Toast.LENGTH_SHORT).show();
            }
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void navigateAbout() {
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
        intent.putExtra("urlString", "http://msccsharjah.com/Pages/About.php?");
        startActivity(intent);
    }

    public class generateBubbleNotification extends AsyncTask<String, Void, Bitmap> {

        private Context mContext;
        private PushNotification pushNotification;

        generateBubbleNotification(Context context, PushNotification pushNotification) {
            super();
            this.mContext = context;
            this.pushNotification = pushNotification;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            try {
                URL url = new URL(this.pushNotification.getImage());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                return BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            Intent intent;

            if (pushNotification.getAnniversary() || pushNotification.getBirthday()) {
                intent = new Intent(mContext, PushNotificationActivity.class);
                intent.putExtra(PushNotificationActivity.TAG_PUSH, pushNotification);
            } else if (pushNotification.getNotification()) {
                intent = new Intent(mContext, NotificationActivity.class);
            } else if (pushNotification.getArticle()) {
                intent = new Intent(mContext, ArticlesActivity.class);
            } else {
                intent = new Intent(mContext, MainActivity.class);
            }

            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 100, intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notif;

            Uri alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


            notif = new Notification.Builder(mContext)
                    .setContentIntent(pendingIntent)
                    .setContentTitle(pushNotification.getTitle())
                    .setContentText(pushNotification.getMessage())
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setSound(alertSound)
                    .setLargeIcon(result)
                    .setStyle(new Notification.BigTextStyle().bigText(pushNotification.getTitle()))
                    .build();


            notif.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(1, notif);
        }
    }

}
