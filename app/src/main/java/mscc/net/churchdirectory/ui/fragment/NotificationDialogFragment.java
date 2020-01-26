package mscc.net.churchdirectory.ui.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

public class NotificationDialogFragment extends DialogFragment {
    private String notification;
    public static final String NOTIFICATION = "notif";

    public static NotificationDialogFragment newInstance(String notification) {

        Bundle args = new Bundle();
        args.putString(NOTIFICATION, notification);

        NotificationDialogFragment fragment = new NotificationDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        notification = getArguments().getString(NOTIFICATION);

        if(notification.length()> 0){
            notification =  notification.substring(0, 1).toUpperCase() + notification.substring(1);
        }

        return new AlertDialog.Builder(getActivity())
                .setMessage(notification)
                .setNegativeButton("Ok", (dialogInterface, i) -> dismiss())
                .create();
    }
}

