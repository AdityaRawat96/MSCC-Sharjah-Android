package mscc.net.churchdirectory.ui.activity;

import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import mscc.net.churchdirectory.ui.fragment.PrayerGroupFragment;

public class PrayerFragmentLauncher extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new PrayerGroupFragment()).commit();}
    }
}
