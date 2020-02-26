package mscc.net.churchdirectory.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.ui.activity.WebViewActivity;

public class PiousAccociationsFragment extends Fragment {

    private ImageView MCCLImageView;
    private ImageView MCYMImageView;
    private ImageView MathrusamajamImageView;

    public PiousAccociationsFragment() {
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
        View RootView = inflater.inflate(R.layout.fragment_pious_accociations, container, false);

        MCCLImageView = RootView.findViewById(R.id.MCCLImageView);
        MCYMImageView = RootView.findViewById(R.id.MCYMImageView);
        MathrusamajamImageView = RootView.findViewById(R.id.MATHRUSAMAJAMImageView);

        MCCLImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/MCCL.php");
                startActivity(intent);
            }
        });

        MCYMImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/MCYM.php");
                startActivity(intent);
            }
        });

        MathrusamajamImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/Mathrusmajam.php");
                startActivity(intent);
            }
        });

        return RootView;
    }

}
