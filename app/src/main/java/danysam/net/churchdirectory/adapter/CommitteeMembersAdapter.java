package danysam.net.churchdirectory.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import java.util.List;

import danysam.net.churchdirectory.R;
import danysam.net.churchdirectory.model.CommitteeMember;

/**
 * Created by Dany on 16-01-2018.
 */

public class CommitteeMembersAdapter extends RecyclerView.Adapter<CommitteeMembersAdapter.ViewHolder> {

    private List<CommitteeMember> committeeMembers;
    private Context context;

    public CommitteeMembersAdapter(List<CommitteeMember> committeeMembers, Context context) {
        this.committeeMembers = committeeMembers;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommitteeMembersAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_committee_member, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(committeeMembers.get(position).getCommitteeMemberName());
        holder.designation.setText(committeeMembers.get(position).getCommitteeMemberDesignation());
        Uri imageURI = Uri.parse("http://msccsharjah.com/CommitteeMember/" + committeeMembers.get(position).getCommitteeMemberImage() + "?v=" + committeeMembers.get(position).getCommitteeMemberTimestamp());
        Picasso.get().load(imageURI).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return committeeMembers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView designation;
        private ImageView image;

        ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.card_committee_member_name);
            designation = itemView.findViewById(R.id.card_committee_member_designation);
            image = itemView.findViewById(R.id.card_committee_member_image);
        }
    }
}
