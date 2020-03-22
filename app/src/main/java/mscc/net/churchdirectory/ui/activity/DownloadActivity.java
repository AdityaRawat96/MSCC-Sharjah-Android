package mscc.net.churchdirectory.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.adapter.DownloadAdapter;
import mscc.net.churchdirectory.connection.ConnectionManager;
import mscc.net.churchdirectory.model.Downloads;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class DownloadActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DownloadAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<Downloads.Download> downloads;
    private Toolbar toolbar;

    private Observable<Response<Downloads>> getDownloads() {
        return ConnectionManager
                .getInstance()
                .getClient()
                .getDownloads()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Liturgical Downloads");

        recyclerView = findViewById(R.id.recycler_download);
        downloads = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        adapter = new DownloadAdapter(downloads, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        getDownloads().subscribe(this::setDownloads, this::setError);

    }

    private void setError(Throwable throwable) {
        Log.e("Network Error", throwable.getLocalizedMessage());
    }

    private void setDownloads(Response<Downloads> response) {
        if (response.isSuccessful()) {
            downloads.addAll(response.body().getResponse().getDownloads());
            adapter.notifyDataSetChanged();
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
}
