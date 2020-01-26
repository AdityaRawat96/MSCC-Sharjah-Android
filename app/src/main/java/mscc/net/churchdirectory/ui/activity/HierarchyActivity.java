package mscc.net.churchdirectory.ui.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mscc.net.churchdirectory.database.MetropolitianDatabase;
import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.adapter.HierarchyAdapter;
import mscc.net.churchdirectory.model.Metropolitian;

public class HierarchyActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbar;
    private RecyclerView recyclerView;
    private HierarchyAdapter adapter;
    private String TAG = HierarchyActivity.class.getSimpleName();
    private static String url = "http://msccsharjah.com/api/hierarchy/";
    private LinearLayoutManager layoutManager;
    List<Metropolitian> metropolitians = new ArrayList<>();
    List<Metropolitian> metropolitiansUpdated = new ArrayList<>();
    MetropolitianDatabase metropolitianDatabase = new MetropolitianDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hierarchy);
        recyclerView = findViewById(R.id.recycler_hierarchy);
        toolbar = findViewById(R.id.toolbar);

        metropolitians = metropolitianDatabase.getAllMetropolitians();
        Collections.sort(metropolitians, (o1, o2) -> o1.getMetropolitianRank().compareTo(o2.getMetropolitianRank()));

        layoutManager = new LinearLayoutManager(this);
        adapter = new HierarchyAdapter(metropolitians, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Synodal Metropolitian");

        if(haveNetworkConnection() == true){
            new GetMetropolitian().execute();
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

    private void initHierarchies() {
        metropolitians.clear();
        metropolitians = metropolitianDatabase.getAllMetropolitians();
        recyclerView.setAdapter(new HierarchyAdapter(metropolitians, this));
        recyclerView.invalidate();
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

    private class GetMetropolitian extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            metropolitianDatabase.deleteAllRecords();
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
                    JSONArray metropolitianArray = jsonObj.getJSONArray("response");

                    // looping through All Contacts
                    for (int i = 0; i < metropolitianArray.length(); i++) {
                        JSONObject m = metropolitianArray.getJSONObject(i);

                        Metropolitian metropolitian = new Metropolitian();

                        metropolitian.setMetropolitianId(m.getString("id"));
                        metropolitian.setMetropolitianName(m.getString("metropolitianName"));
                        metropolitian.setMetropolitianDesignation(m.getString("metropolitianDesignation"));
                        metropolitian.setMetropolitianRank(m.getString("metropolitianRank"));
                        metropolitian.setMetropolitianImage(m.getString("metropolitianImage"));
                        metropolitian.setMetropolitianTimestamp(m.getString("metropolitianTimestamp"));

                        metropolitianDatabase.addItem(metropolitian);
                        metropolitiansUpdated.add(metropolitian);
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
            if(! metropolitiansUpdated.equals(metropolitians)){
                initHierarchies();
            }
        }

    }
}
