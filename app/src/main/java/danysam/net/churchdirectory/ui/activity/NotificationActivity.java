package danysam.net.churchdirectory.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import danysam.net.churchdirectory.R;
import danysam.net.churchdirectory.ui.fragment.NotificationsFragment;

public class NotificationActivity extends AppCompatActivity {

    private NotificationsFragment fragment;
    private FragmentManager manager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("News");

        fragment = new NotificationsFragment();

        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame_notifications, fragment, "notificationFragment").commit();
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
