package danysam.net.churchdirectory.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import danysam.net.churchdirectory.R;
import danysam.net.churchdirectory.connection.ConnectionUtil;
import danysam.net.churchdirectory.model.Notification;
import danysam.net.churchdirectory.ui.activity.NotificationDetailsActivity;

import static danysam.net.churchdirectory.ui.activity.NotificationDetailsActivity.TAG_PARCELABLE_NOTIFICATION;

/**
 * Created by Dany on 03-01-2018.
 */

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    private List<Notification.Notification_> notifications;
    private Context context;

    public NotificationsAdapter(List<Notification.Notification_> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_notification, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.dateTime.setText(notifications.get(position).getDate());
        holder.message.setText(notifications.get(position).getTitle());

        if (notifications.get(position).getImage() != null) {
            if (!notifications.get(position).getImage().isEmpty()) {
                Picasso.get().load(notifications.get(position).getImage())
                        .error(R.drawable.ic_placeholder_background)
                        .placeholder(R.drawable.ic_placeholder_background)
                        .centerCrop()
                        .resize(100, 100)
                        .into(holder.image);
            }
        }

        holder.cardView.setOnClickListener(v -> {
            Intent i = new Intent(context, NotificationDetailsActivity.class);
            i.putExtra(TAG_PARCELABLE_NOTIFICATION, notifications.get(position));
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView dateTime;
        private TextView message;
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.notification_image);
            message = itemView.findViewById(R.id.notification_text);
            dateTime = itemView.findViewById(R.id.notification_date);
            cardView = itemView.findViewById(R.id.cardview_notification);
        }
    }
}
