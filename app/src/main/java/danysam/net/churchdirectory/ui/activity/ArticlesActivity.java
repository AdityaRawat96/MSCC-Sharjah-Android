package danysam.net.churchdirectory.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import danysam.net.churchdirectory.R;
import danysam.net.churchdirectory.ui.fragment.ArticlesFragment;

public class ArticlesActivity extends AppCompatActivity {

    private ArticlesFragment fragment;
    private FragmentManager manager;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Articles");

        fragment = new ArticlesFragment();

        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame_articles, fragment, "articlesFragment").commit();


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
