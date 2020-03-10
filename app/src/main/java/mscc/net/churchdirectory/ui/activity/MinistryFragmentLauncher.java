package mscc.net.churchdirectory.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import mscc.net.churchdirectory.ui.fragment.OtherMinistriesFragment;
import mscc.net.churchdirectory.ui.fragment.PiousAccociationsFragment;

public class MinistryFragmentLauncher extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new OtherMinistriesFragment()).commit();}
    }
}
