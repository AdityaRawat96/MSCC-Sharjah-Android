package mscc.net.churchdirectory.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.room.model.Contact;

/**
 * Created by Dany on 02-01-2018.
 */

public class ContactNumbersAdapter extends RecyclerView.Adapter<ContactNumbersAdapter.ViewHolder> {

    public static final String TYPE_EMAIL = "email";

    public ContactNumbersAdapter(List<Contact> contactDetails, Context context) {
        this.contactDetails = contactDetails;
        this.context = context;
        this.isEditableCard = false;
    }

    public ContactNumbersAdapter(List<Contact> contactDetails, Context context, Boolean isEditableCard) {
        this.contactDetails = contactDetails;
        this.context = context;
        this.isEditableCard = isEditableCard;
    }

    private List<Contact> contactDetails;
    private Context context;
    private Boolean isEditableCard;
    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_contact_details, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // TODO: 02-01-2018 check for type of contact details
        holder.type.setText(contactDetails.get(position).getName());
        holder.number.setText(contactDetails.get(position).getData());

        if (Objects.equals(contactDetails.get(position).getType(), TYPE_EMAIL)) {
            holder.call.setVisibility(View.GONE);
            holder.message.setVisibility(View.GONE);
            holder.email.setVisibility(View.VISIBLE);
        } else {
            holder.call.setVisibility(View.VISIBLE);
            holder.message.setVisibility(View.VISIBLE);
            holder.email.setVisibility(View.GONE);
        }

        holder.call.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + contactDetails.get(position).getData()));
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                context.startActivity(callIntent);
            } else {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        });

        holder.message.setOnClickListener(view -> {

            if (Objects.equals(contactDetails.get(position).getType(), TYPE_EMAIL)) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{contactDetails.get(position).getData()});
                context.startActivity(Intent.createChooser(intent, "Send Email"));

            } else {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:" + contactDetails.get(position).getData()));
                context.startActivity(sendIntent);
            }

        });

        holder.email.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{contactDetails.get(position).getData()});
            context.startActivity(Intent.createChooser(intent, "Send Email"));
        });
    }

    @Override
    public int getItemCount() {
        return contactDetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView type;
        TextView number;
        ImageButton call;
        ImageButton message;
        ImageButton email;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.contact_details_type);
            number = itemView.findViewById(R.id.contact_details_number);
            call = itemView.findViewById(R.id.contact_details_call);
            message = itemView.findViewById(R.id.contact_details_message);
            email = itemView.findViewById(R.id.contact_details_email);
            cardView = itemView.findViewById(R.id.contact_details_card);
        }
    }
}
