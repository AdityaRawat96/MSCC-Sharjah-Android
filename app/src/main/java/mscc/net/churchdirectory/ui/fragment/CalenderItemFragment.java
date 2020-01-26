package mscc.net.churchdirectory.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsibbold.zoomage.ZoomageView;

import mscc.net.churchdirectory.R;

public class CalenderItemFragment extends Fragment {

    private static final String ARG_IMAGE_ID = "calender_image_id";
    private int imageId;
    private ZoomageView imageView;

    public CalenderItemFragment() {
        // Required empty public constructor
    }

    public static CalenderItemFragment newInstance(int param1) {
        CalenderItemFragment fragment = new CalenderItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageId = getArguments().getInt(ARG_IMAGE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calender_item, container, false);
        imageView = view.findViewById(R.id.calender_image);
        imageView.setImageDrawable(getResources().getDrawable(imageId));
        return view;
    }

}
