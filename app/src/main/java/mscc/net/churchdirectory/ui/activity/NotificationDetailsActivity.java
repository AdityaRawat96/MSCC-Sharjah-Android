package mscc.net.churchdirectory.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import mscc.net.churchdirectory.model.Notification;
import com.squareup.picasso.Picasso;

import mscc.net.churchdirectory.R;

public class NotificationDetailsActivity extends AppCompatActivity {

    public static final String TAG_PARCELABLE_NOTIFICATION = "tag_parcelable_notification";
    private Notification.Notification_ notification;
    private Toolbar toolbar;
    private ImageView image;
    private TextView title;
    private TextView message;
    private Button button;
    private CardView messageSection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        toolbar = findViewById(R.id.toolbar);
        image = findViewById(R.id.notification_image);
        title = findViewById(R.id.notification_title);
        button = findViewById(R.id.readMoreButtonNews);
        message = findViewById(R.id.notification_message);
        messageSection = findViewById(R.id.cardview_notification_message);

        notification = getIntent().getParcelableExtra(TAG_PARCELABLE_NOTIFICATION);

        if (notification == null) {
            finish();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        title.setText(notification.getTitle());

        if(notification.getLink().length() > 4){
            button.setVisibility(View.VISIBLE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NotificationDetailsActivity.this, WebViewActivity.class);
                intent.putExtra("urlString", notification.getLink());
                startActivity(intent);
            }
        });

        Picasso.get().load(notification.getImage())
                .error(R.drawable.ic_placeholder_background)
                .placeholder(R.drawable.ic_placeholder_background)
                .into(image);

        messageSection.setVisibility(notification.getMessage().length() > 0 ? View.VISIBLE : View.GONE);
        message.setText(notification.getMessage());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
