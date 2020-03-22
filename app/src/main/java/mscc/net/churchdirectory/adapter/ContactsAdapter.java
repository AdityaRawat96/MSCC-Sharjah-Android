package mscc.net.churchdirectory.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mscc.net.churchdirectory.ui.activity.ContactDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import mscc.net.churchdirectory.R;

import mscc.net.churchdirectory.model.Directory;

/**
 * Created by Dany on 01-01-2018.
 * Adapter for contacts
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private List<Directory.Family> contacts;
    private Context context;

    public ContactsAdapter(List<Directory.Family> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(contacts.get(position).getName());
        holder.address.setText(contacts.get(position).getPermanentAddress());
        holder.phone.setText(getPrimaryContact(contacts.get(position).getContacts()));

        if (contacts.get(position).getImage() != null) {
            if (!contacts.get(position).getImage().isEmpty()) {
                Picasso.get().load(contacts.get(position).getImage())
                        .error(R.drawable.ic_placeholder_background)
                        .centerCrop()
                        .resize(120, 120)
                        .placeholder(R.drawable.ic_placeholder_background)
                        .into(holder.image);
            }
        }

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ContactDetailsActivity.class);
            intent.putExtra(ContactDetailsActivity.KEY_CONTACT, contacts.get(position).getId());

            context.startActivity(intent);

        });
    }

    private String getPrimaryContact(List<Directory.Contact> contacts) {
        String primary = "";
        for (Directory.Contact c : contacts) {
            if (c.getType().equals("mobile")) {
                primary = c.getData();
            }
        }

        return primary;
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView address;
        TextView phone;
        CardView cardView;
        ImageView image;
        CardView imageHolder;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.card_contact_name);
            address = itemView.findViewById(R.id.card_contact_address);
            phone = itemView.findViewById(R.id.card_contact_phone);
            cardView = itemView.findViewById(R.id.card_contact);
            image = itemView.findViewById(R.id.card_contact_image);
            imageHolder = itemView.findViewById(R.id.cardview_contact_image);
        }
    }
}
