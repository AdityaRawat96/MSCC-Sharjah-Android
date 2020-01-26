package mscc.net.churchdirectory.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mscc.net.churchdirectory.R;

public class AdvertisementActivity extends AppCompatActivity {

    public ImageView adImage;
    public Button webButton;
    public Button callButton;
    public TextView adDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String description = intent.getStringExtra("description");
        String image = intent.getStringExtra("image");
        String website = intent.getStringExtra("website");
        String phone = intent.getStringExtra("phone");
        String timestamp = intent.getStringExtra("timestamp");

        adImage = findViewById(R.id.imageViewFullAd);
        adDescription = findViewById(R.id.descriptionTextViewAd);
        webButton = findViewById(R.id.visitButton);
        callButton = findViewById(R.id.callButton);

        adDescription.setText(description);

        Uri imageURI = Uri.parse("http://msccsharjah.com/imagesAds/full/" + image + "?" + timestamp);
        Picasso.get().load(imageURI).into(adImage);

        toolbar.setTitle(name);
        getSupportActionBar().setTitle(name);

        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri webpage = Uri.parse(website);
                if (!website.startsWith("http://") && !website.startsWith("https://")) {
                    webpage = Uri.parse("http://" + website);
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        callButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
