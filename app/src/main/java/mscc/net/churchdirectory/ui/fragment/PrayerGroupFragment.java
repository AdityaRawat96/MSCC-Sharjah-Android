package mscc.net.churchdirectory.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.ui.activity.PiousAssociationsActivity;
import mscc.net.churchdirectory.ui.activity.WebViewActivity;

public class PrayerGroupFragment extends Fragment {

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
    private Toolbar toolbar;

    public PrayerGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View RootView = inflater.inflate(R.layout.fragment_prayer_group, container, false);

        toolbar = RootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Prayer Groups");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PiousAssociationsActivity.class));
            }
        });

        OurLadyOfArabiaImageView = RootView.findViewById(R.id.OurLadyOfArabiaImageView);
        StPeterImageView = RootView.findViewById(R.id.StPetersImageView);
        StJosephImageView = RootView.findViewById(R.id.StJosephImageView);
        StGeorgeImageView = RootView.findViewById(R.id.StGeorgeImageView);
        StJohnsImageView = RootView.findViewById(R.id.StJohnsImageView);
        StJudeImageView = RootView.findViewById(R.id.StJudeImageView);
        StThomasImageView = RootView.findViewById(R.id.StThomasImageView);
        StAlphonsaImageView = RootView.findViewById(R.id.StAlphonsaImageView);
        MarIavniosImageView = RootView.findViewById(R.id.MarIvaniosImageView);
        StFrancisOfAssisiImageView = RootView.findViewById(R.id.StFrancisOfAssisiImageView);
        StTheresaImageView = RootView.findViewById(R.id.StTheresaImageView);
        MarGregorisImageView = RootView.findViewById(R.id.MarGregorisImageView);

        OurLadyOfArabiaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/OurLadyOfArabia.php");
                startActivity(intent);
            }
        });

        StPeterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StPeter.php");
                startActivity(intent);
            }
        });

        StJosephImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StJoseph.php");
                startActivity(intent);
            }
        });

        StGeorgeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StGeorge.php");
                startActivity(intent);
            }
        });

        StJohnsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StJohn.php");
                startActivity(intent);
            }
        });

        StJudeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StJude.php");
                startActivity(intent);
            }
        });

        StThomasImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StThomas.php");
                startActivity(intent);
            }
        });

        StAlphonsaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StAlphonsa.php");
                startActivity(intent);
            }
        });

        MarIavniosImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/MarIvanios.php");
                startActivity(intent);
            }
        });

        StFrancisOfAssisiImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StFrancisofAssisi.php");
                startActivity(intent);
            }
        });

        StTheresaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/StTheresa.php");
                startActivity(intent);
            }
        });

        MarGregorisImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/MarGregorios.php");
                startActivity(intent);
            }
        });

        return RootView;


    }

}
