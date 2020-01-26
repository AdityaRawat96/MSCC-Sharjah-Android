package mscc.net.churchdirectory.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.stfalcon.frescoimageviewer.ImageViewer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mscc.net.churchdirectory.database.CalendarDatabase;
import mscc.net.churchdirectory.MainActivity;
import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.model.Calendar;

public class CalendarActivity extends AppCompatActivity {

    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> listUpdated = new ArrayList<>();
    List<Calendar> monthList = new ArrayList<>();
    private String TAG = CalendarActivity.class.getSimpleName();
    CalendarDatabase calendarDatabase = new CalendarDatabase(this);
    private int monthNumber;
    private static String url = "http://msccsharjah.com/api/calendar/";
    private GenericDraweeHierarchyBuilder hierarchyBuilder;
    private int imagePosition;
    private ImagePipelineConfig config;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Calendar");
        getSupportActionBar().setTitle("Calendar");

        config = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .setResizeAndRotateEnabledForNetwork(true)
                .setDownsampleEnabled(true)
                .build();

        Fresco.initialize(this, config);

        hierarchyBuilder = GenericDraweeHierarchyBuilder.newInstance(getResources())
                .setFailureImage(R.drawable.loading)
                .setProgressBarImage(R.drawable.loading)
                .setPlaceholderImage(R.drawable.loading);

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        monthNumber = localDate.getMonthValue() - 1;
        monthList = calendarDatabase.getAllMonths();
        for(int i =0; i < monthList.size(); i++){
            list.add("http://msccsharjah.com/Calendar/"+monthList.get(i).getMonthName()+".png?v="+monthList.get(i).getMonthTimestamp());
        }
        new ImageViewer.Builder(CalendarActivity.this, list).allowSwipeToDismiss(false).setOnDismissListener(getDismissListener()).setImageChangeListener(getImageChangeListener()).hideStatusBar(false).setCustomDraweeHierarchyBuilder(hierarchyBuilder).setStartPosition(monthNumber).show();

        if(haveNetworkConnection() == true){
            new GetCalendar().execute();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private ImageViewer.OnImageChangeListener getImageChangeListener() {
        return new ImageViewer.OnImageChangeListener() {
            @Override
            public void onImageChange(int position) {
                imagePosition = position;
            }
        };
    }

    private ImageViewer.OnDismissListener getDismissListener() {
        return new ImageViewer.OnDismissListener() {
            @Override
            public void onDismiss() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        };
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


    private class GetCalendar extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                    JSONArray calendarArray = jsonObj.getJSONArray("response");

                    // looping through All Contacts
                    for (int i = 0; i < calendarArray.length(); i++) {
                        JSONObject c = calendarArray.getJSONObject(i);

                        Calendar calendar = new Calendar();

                        calendar.setMonthId(c.getString("id"));
                        calendar.setMonthName(c.getString("month"));
                        calendar.setMonthTimestamp(c.getString("timestamp"));
                        calendarDatabase.deleteRecords(calendar);
                        calendarDatabase.addItem(calendar);
                        listUpdated.add("http://msccsharjah.com/Calendar/"+c.getString("month")+".png?v="+c.getString("timestamp"));
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
            new ImageViewer.Builder(CalendarActivity.this, listUpdated).allowSwipeToDismiss(false).setOnDismissListener(getDismissListener()).setStartPosition(imagePosition).setImageChangeListener(getImageChangeListener()).setCustomDraweeHierarchyBuilder(hierarchyBuilder).hideStatusBar(false).show();

        }

    }

}
