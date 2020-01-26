package mscc.net.churchdirectory.adapter;

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

import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.model.Metropolitian;

/**
 * Created by Dany on 18-01-2018.
 */

public class HierarchyAdapter extends RecyclerView.Adapter<HierarchyAdapter.ViewHolder> {

    private List<Metropolitian> metropolitians;
    private Context context;

    public HierarchyAdapter(List<Metropolitian> metropolitians, Context context) {
        this.metropolitians = metropolitians;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HierarchyAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_hierarchy, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Uri imageURI = Uri.parse("http://msccsharjah.com/Hierarchy/" + metropolitians.get(position).getMetropolitianImage() + "?v=" + metropolitians.get(position).getMetropolitianTimestamp());
        Picasso.get().load(imageURI).into(holder.image);
        holder.name.setText(metropolitians.get(position).getMetropolitianName());
        holder.designation.setText(metropolitians.get(position).getMetropolitianDesignation());
    }
    @Override
    public int getItemCount() {
        return metropolitians.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private TextView name;
        private TextView designation;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.card_hierarchy_image);
            name = itemView.findViewById(R.id.card_hierarchy_name);
            designation = itemView.findViewById(R.id.card_hierarchy_designation);
        }
    }
}
