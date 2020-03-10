package mscc.net.churchdirectory.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.ui.activity.PiousAssociationsActivity;
import mscc.net.churchdirectory.ui.activity.WebViewActivity;

public class OtherMinistriesFragment extends Fragment {

    private ImageView OurLadyOfArabiaImageView;
    private Toolbar toolbar;

    public OtherMinistriesFragment() {
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
        View RootView = inflater.inflate(R.layout.fragment_other_ministries, container, false);

        toolbar = RootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Other Ministires");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PiousAssociationsActivity.class));
            }
        });

        OurLadyOfArabiaImageView = RootView.findViewById(R.id.OurLadyOfArabiaImageView);

        OurLadyOfArabiaImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , WebViewActivity.class);
                intent.putExtra("urlString", "http://msccsharjah.com/Pages/otherMinistries.php");
                startActivity(intent);
            }
        });

        return RootView;
    }

}
