package danysam.net.churchdirectory.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.widget.Toolbar;

import danysam.net.churchdirectory.LoginActivity;
import danysam.net.churchdirectory.R;
import danysam.net.churchdirectory.session.SessionManager;
import danysam.net.churchdirectory.ui.fragment.ContactsFragment;

public class ContactsActivity extends AppCompatActivity {

    private ContactsFragment fragment;
    private FragmentManager manager;
    private android.support.v7.widget.Toolbar toolbar;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        toolbar = findViewById(R.id.toolbar);
        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fragment.searchContacts(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fragment.searchContacts(newText);
                return true;
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contacts");

        fragment = new ContactsFragment();

        manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frame_contacts, fragment, "contactsFragment").commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!SessionManager.getInstance().isLoggedIn(this)) {
            startActivity(new Intent(this, LoginActivity.class));
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
