package mscc.net.churchdirectory.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.List;

import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.model.Committee;
import mscc.net.churchdirectory.ui.activity.CommitteeMembersActivity;

/**
 * Created by Dany on 16-01-2018.
 */

public class CommitteeAdapter extends RecyclerView.Adapter<CommitteeAdapter.ViewHolder> {

    private List<Committee> committees;
    private Context context;

    public CommitteeAdapter(List<Committee> committees, Context context) {
        this.committees = committees;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommitteeAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_committees, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(committees.get(position).getCommitteeName());
        Uri imageURI = Uri.parse("http://msccsharjah.com/Committee/" + committees.get(position).getCommitteeImage() + "?v=" + committees.get(position).getCommitteeTimestamp());
        Picasso.get().load(imageURI).into(holder.image);

        if (position == 0) {
            holder.duration.setText("Present committee");
            holder.cardView.setOnClickListener(view -> {
                Intent i = new Intent(context, CommitteeMembersActivity.class);
                i.putExtra("PresentCommittee", true);
                i.putExtra("CommitteeId", committees.get(position).getCommitteeId());
                i.putExtra("CommitteeName", committees.get(position).getCommitteeName());
                context.startActivity(i);
            });
        }

        else {
            holder.cardView.setOnClickListener(view -> {
                Intent i = new Intent(context, CommitteeMembersActivity.class);
                i.putExtra("PresentCommittee", false);
                i.putExtra("CommitteeId", committees.get(position).getCommitteeId());
                i.putExtra("CommitteeName", committees.get(position).getCommitteeName());
                context.startActivity(i);
            });
        }

    }

    @Override
    public int getItemCount() {
        return committees.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView duration;
        private ImageView image;
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.card_committee_name);
            duration = itemView.findViewById(R.id.card_committee_duration);
            image = itemView.findViewById(R.id.card_committee_image);
            cardView = itemView.findViewById(R.id.card_committee);

        }
    }
}
