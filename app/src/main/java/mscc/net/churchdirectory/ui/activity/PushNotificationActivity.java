package mscc.net.churchdirectory.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import mscc.net.churchdirectory.MainActivity;
import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.model.PushNotification;

@SuppressWarnings("FieldCanBeLocal")
public class PushNotificationActivity extends AppCompatActivity {

    public static final String TAG_PUSH = "parcelable_push_notification";
    private PushNotification pushNotification;

    private TextView title;
    private ImageView image;
    private Button enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notification);

        title = findViewById(R.id.notification_title);
        image = findViewById(R.id.notification_image);
        enter = findViewById(R.id.button_enter);

        enter.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });

        pushNotification = getIntent().getParcelableExtra(TAG_PUSH);

        title.setText(pushNotification.getTitle());

        Picasso.get().load(pushNotification.getImage())
                .error(R.drawable.ic_placeholder_background)
                .placeholder(R.drawable.ic_placeholder_background)
                .into(image);
    }
}
