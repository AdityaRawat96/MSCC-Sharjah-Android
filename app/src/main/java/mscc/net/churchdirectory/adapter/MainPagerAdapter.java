package mscc.net.churchdirectory.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import mscc.net.churchdirectory.ui.fragment.ArticlesFragment;
import mscc.net.churchdirectory.ui.fragment.ContactsFragment;
import mscc.net.churchdirectory.ui.fragment.NotificationsFragment;

/**
 * Created by Dany on 02-01-2018.
 */

public class MainPagerAdapter extends FragmentStatePagerAdapter {

    public MainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ContactsFragment();

            case 1:
                return new ArticlesFragment();

            default:
                return new NotificationsFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Contacts";

            case 1:
                return "Articles";

            case 2:
                return "Notifications";

            default:
                return "";

        }
    }
}
