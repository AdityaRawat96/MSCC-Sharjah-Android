package mscc.net.churchdirectory.ui.activity;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import mscc.net.churchdirectory.ui.fragment.PiousAccociationsFragment;

public class PiousFragmentLauncher extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new PiousAccociationsFragment()).commit();}
    }
}