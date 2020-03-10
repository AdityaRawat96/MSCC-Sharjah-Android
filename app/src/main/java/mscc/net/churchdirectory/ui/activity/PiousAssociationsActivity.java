package mscc.net.churchdirectory.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import mscc.net.churchdirectory.R;

public class PiousAssociationsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout piousLayout;
    private LinearLayout prayerLayout;
    private LinearLayout ministryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pious_associations);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Pious Associations");

        piousLayout = findViewById(R.id.piousLayout);
        prayerLayout = findViewById(R.id.prayerLayout);
        ministryLayout = findViewById(R.id.ministryLayout);

        piousLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(PiousAssociationsActivity.this, R.anim.click_anim));
                Intent i = new Intent(PiousAssociationsActivity.this, PiousFragmentLauncher.class);
                startActivity(i);
            }
        });

        prayerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(PiousAssociationsActivity.this, R.anim.click_anim));
                Intent i = new Intent(PiousAssociationsActivity.this, PrayerFragmentLauncher.class);
                startActivity(i);
            }
        });

        ministryLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(PiousAssociationsActivity.this, R.anim.click_anim));
                Intent intent = new Intent(PiousAssociationsActivity.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/otherMinistries.php");
                startActivity(intent);
            }
        });

    }


}
