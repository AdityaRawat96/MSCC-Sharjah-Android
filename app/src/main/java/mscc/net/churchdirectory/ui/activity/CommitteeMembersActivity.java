package mscc.net.churchdirectory.ui.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import mscc.net.churchdirectory.adapter.CommitteeMembersAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mscc.net.churchdirectory.database.CommitteeMemberDatabase;
import mscc.net.churchdirectory.R;

import mscc.net.churchdirectory.model.CommitteeMember;

public class CommitteeMembersActivity extends AppCompatActivity {

    private CommitteeMembersAdapter adapter;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager1;
    private RecyclerView.LayoutManager layoutManager2;
    private Toolbar toolbar;
    private String TAG = CommitteeMembersActivity.class.getSimpleName();
    private static String url;
    private List<CommitteeMember> committeeMemberList;
    List<CommitteeMember> committeeMemberListUpdated = new ArrayList<>();
    CommitteeMemberDatabase committeeMemberDatabase = new CommitteeMemberDatabase(this);


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_committee_members);
        recyclerView1 = findViewById(R.id.recycler_committee_members_executive);
        recyclerView2 = findViewById(R.id.recycler_committee_members);
        toolbar = findViewById(R.id.toolbar);

        committeeMemberList = committeeMemberDatabase.getAllCommitteeMembers();
        Collections.sort(committeeMemberList, (o1, o2) -> o1.getCommitteeMemberRank().compareTo(o2.getCommitteeMemberRank()));

        layoutManager1 = new LinearLayoutManager(this);
        layoutManager2 = new LinearLayoutManager(this);
        adapter = new CommitteeMembersAdapter(committeeMemberList, this);

        recyclerView1.setAdapter(adapter);
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.setHasFixedSize(true);

        recyclerView2.setAdapter(adapter);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setHasFixedSize(true);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getIntent().getExtras()!=null){
            String committeeId = getIntent().getStringExtra("CommitteeId");
            String committeeName = getIntent().getStringExtra("CommitteeName");
            url = "http://msccsharjah.com/api/committee_members/?id="+committeeId;
            if(getIntent().getBooleanExtra("PresentCommittee", false) == true){
                getSupportActionBar().setTitle("Present Committee");
            }else{
                getSupportActionBar().setTitle(committeeName);
            }
        }else{
            finish();
        }

        if(haveNetworkConnection() == true){
            new GetCommitteeMember().execute();
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
        committeeMemberList.clear();
        committeeMemberList = committeeMemberDatabase.getAllCommitteeMembers();
        List<CommitteeMember> list1 = new ArrayList<>();
        List<CommitteeMember> list2 = new ArrayList<>();
        for(int i = 0; i < committeeMemberList.size(); i++){
            if(committeeMemberList.get(i).getExecutiveMember().equalsIgnoreCase("1")){
                list1.add(committeeMemberList.get(i));
            }else {
                list2.add(committeeMemberList.get(i));
            }
        }
        recyclerView1.setAdapter(new CommitteeMembersAdapter(list1, this));
        recyclerView1.invalidate();
        recyclerView2.setAdapter(new CommitteeMembersAdapter(list2, this));
        recyclerView2.invalidate();
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

    private class GetCommitteeMember extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            committeeMemberDatabase.deleteAllRecords();
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
                    JSONArray committeeMemberArray = jsonObj.getJSONArray("response");

                    // looping through All Contacts
                    for (int i = 0; i < committeeMemberArray.length(); i++) {
                        JSONObject m = committeeMemberArray.getJSONObject(i);

                        CommitteeMember committeeMember = new CommitteeMember();

                        committeeMember.setCommitteeMemberId(m.getString("id"));
                        committeeMember.setCommitteeMemberName(m.getString("committeeMemberName"));
                        committeeMember.setCommitteeMemberImage(m.getString("committeeMemberImage"));
                        committeeMember.setCommitteeMemberDesignation(m.getString("committeeMemberDesignation"));
                        committeeMember.setCommitteeMemberMemberOf(m.getString("committeeMemberOf"));
                        committeeMember.setCommitteeMemberTimestamp(m.getString("committeeMemberTimestamp"));
                        committeeMember.setCommitteeMemberRank(m.getString("committeeMemberRank"));
                        committeeMember.setExecutiveMember(m.getString("executiveCommitteeMember"));

                        committeeMemberDatabase.addItem(committeeMember);
                        committeeMemberListUpdated.add(committeeMember);
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

