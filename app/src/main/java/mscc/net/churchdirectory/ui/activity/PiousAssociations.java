package mscc.net.churchdirectory.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import mscc.net.churchdirectory.R;

public class PiousAssociations extends AppCompatActivity {

    private ImageView MCCLImageView;
    private ImageView MCYMImageView;
    private ImageView MathrusamajamImageView;
    private ImageView OurLadyOfArabiaImageView;
    private ImageView StPeterImageView;
    private ImageView StJosephImageView;
    private ImageView StGeorgeImageView;
    private ImageView StJohnsImageView;
    private ImageView StJudeImageView;
    private ImageView StThomasImageView;
    private ImageView StAlphonsaImageView;
    private ImageView MarIavniosImageView;
    private ImageView StFrancisOfAssisiImageView;
    private ImageView StTheresaImageView;
    private ImageView MarGregorisImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pious_associations);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MCCLImageView = findViewById(R.id.MCCLImageView);
        MCYMImageView = findViewById(R.id.MCYMImageView);
        MathrusamajamImageView = findViewById(R.id.MATHRUSAMAJAMImageView);
        OurLadyOfArabiaImageView = findViewById(R.id.OurLadyOfArabiaImageView);
        StPeterImageView = findViewById(R.id.StPetersImageView);
        StJosephImageView = findViewById(R.id.StJosephImageView);
        StGeorgeImageView = findViewById(R.id.StGeorgeImageView);
        StJohnsImageView = findViewById(R.id.StJohnsImageView);
        StJudeImageView = findViewById(R.id.StJudeImageView);
        StThomasImageView = findViewById(R.id.StThomasImageView);
        StAlphonsaImageView = findViewById(R.id.StAlphonsaImageView);
        MarIavniosImageView = findViewById(R.id.MarIvaniosImageView);
        StFrancisOfAssisiImageView = findViewById(R.id.StFrancisOfAssisiImageView);
        StTheresaImageView = findViewById(R.id.StTheresaImageView);
        MarGregorisImageView = findViewById(R.id.MarGregorisImageView);


        MCCLImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/MCCL.php");
                startActivity(intent);
            }
        });

        MCYMImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/MCYM.php");
                startActivity(intent);
            }
        });

        MathrusamajamImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/Mathrusmajam.php");
                startActivity(intent);
            }
        });

        OurLadyOfArabiaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/OurLadyOfArabia.php");
                startActivity(intent);
            }
        });

        StPeterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StPeter.php");
                startActivity(intent);
            }
        });

        StJosephImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StJoseph.php");
                startActivity(intent);
            }
        });

        StGeorgeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StGeorge.php");
                startActivity(intent);
            }
        });

        StJohnsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StJohn.php");
                startActivity(intent);
            }
        });

        StJudeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StJude.php");
                startActivity(intent);
            }
        });

        StThomasImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StThomas.php");
                startActivity(intent);
            }
        });

        StAlphonsaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StAlphonsa.php");
                startActivity(intent);
            }
        });

        MarIavniosImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/MarIvanios.php");
                startActivity(intent);
            }
        });

        StFrancisOfAssisiImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StFrancisofAssisi.php");
                startActivity(intent);
            }
        });

        StTheresaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StTheresa.php");
                startActivity(intent);
            }
        });

        MarGregorisImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PiousAssociations.this , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/MarGregorios.php");
                startActivity(intent);
            }
        });

    }

}
