package danysam.net.churchdirectory.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import danysam.net.churchdirectory.R;
import danysam.net.churchdirectory.room.model.Member;
import danysam.net.churchdirectory.ui.activity.AddMemberActivity;

/**
 * Created by Dany on 02-01-2018.
 */

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.ViewHolder> {

    private List<Member> members;
    private Context context;
    private Boolean isEditMode;
    private int familyId;
    private OnMemberClickListener clickListener;

    public MembersAdapter(List<Member> members, Context context, Boolean isEditMode, int familyId) {
        this.members = members;
        this.context = context;
        this.isEditMode = isEditMode;
        this.familyId = familyId;
        this.clickListener = null;
    }

    public MembersAdapter(List<Member> members, Context context, Boolean isEditMode, int familyId, OnMemberClickListener clickListener) {
        this.members = members;
        this.context = context;
        this.isEditMode = isEditMode;
        this.familyId = familyId;
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.relation.setText(members.get(position).getRelation());
        holder.name.setText(members.get(position).getName());
        holder.dob.setText(members.get(position).getDob());
        holder.bloodGroup.setText(members.get(position).getBloodGroup());
        holder.cardView.setOnClickListener(v -> {

            if (clickListener != null) {
                clickListener.onMemberClicked();
            }

            if (isEditMode) {
                Intent i = new Intent(context, AddMemberActivity.class);
                i.putExtra(AddMemberActivity.EXTRA_FAMILY_ID, familyId);
                i.putExtra(AddMemberActivity.EXTRA_MEMBER_ID, members.get(position).getId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_members_details, parent, false));
    }

    public interface OnMemberClickListener {
        void onMemberClicked();
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView relation;
        TextView name;
        TextView dob;
        TextView bloodGroup;
        CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            relation = itemView.findViewById(R.id.contact_members_relation);
            name = itemView.findViewById(R.id.contact_members_name);
            dob = itemView.findViewById(R.id.contact_members_dob);
            bloodGroup = itemView.findViewById(R.id.contact_members_blood_group);
            cardView = itemView.findViewById(R.id.card_member);
        }
    }
}
