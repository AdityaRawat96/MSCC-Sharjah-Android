package danysam.net.churchdirectory.ui.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import danysam.net.churchdirectory.database.CommitteeDatabase;
import danysam.net.churchdirectory.R;
import danysam.net.churchdirectory.adapter.CommitteeAdapter;
import danysam.net.churchdirectory.model.Committee;

public class CommitteeActivity extends AppCompatActivity {

    private CommitteeAdapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private Toolbar toolbar;
    private String TAG = CommitteeActivity.class.getSimpleName();
    private static String url = "http://msccsharjah.com/api/committee/";
    private List<Committee> committeeList;
    List<Committee> committeeListUpdated = new ArrayList<>();
    CommitteeDatabase committeeDatabase = new CommitteeDatabase(this);


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committe);
        recyclerView = findViewById(R.id.recycler_committee);
        toolbar = findViewById(R.id.toolbar);

        committeeList = committeeDatabase.getAllCommittees();
        Collections.sort(committeeList, (o1, o2) -> o1.getCommitteeRank().compareTo(o2.getCommitteeRank()));

        layoutManager = new LinearLayoutManager(this);
        adapter = new CommitteeAdapter(committeeList, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Committee");

        if(haveNetworkConnection() == true){
            new GetCommittee().execute();
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
        committeeList.clear();
        committeeList = committeeDatabase.getAllCommittees();
        Collections.sort(committeeList, (o1, o2) -> o1.getCommitteeRank().compareTo(o2.getCommitteeRank()));
        recyclerView.setAdapter(new CommitteeAdapter(committeeList, this));
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

    private class GetCommittee extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            committeeDatabase.deleteAllRecords();
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
                    JSONArray committeeArray = jsonObj.getJSONArray("response");

                    // looping through All Contacts
                    for (int i = 0; i < committeeArray.length(); i++) {
                        JSONObject m = committeeArray.getJSONObject(i);

                        Committee committee = new Committee();

                        committee.setCommitteeId(m.getString("id"));
                        committee.setCommitteeName(m.getString("committeeName"));
                        committee.setCommitteeImage(m.getString("committeeImage"));
                        committee.setCommitteeTimestamp(m.getString("committeeTimestamp"));
                        committee.setCommitteeRank(m.getString("committeeRank"));

                        committeeDatabase.addItem(committee);
                        committeeListUpdated.add(committee);
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
            initHierarchies();
        }

    }
}
