package danysam.net.churchdirectory.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import danysam.net.churchdirectory.R;
import danysam.net.churchdirectory.model.Downloads;

/**
 * Created by Dany on 18-01-2018.
 */

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.ViewHolder> {

    private List<Downloads.Download> downloads;
    private Context context;

    public DownloadAdapter(List<Downloads.Download> downloads, Context context) {
        this.downloads = downloads;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DownloadAdapter.ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_download, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(downloads.get(position).getName());

        holder.cardView.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(downloads.get(position).getUrl()));
            context.startActivity(browserIntent);
        });
    }

    @Override
    public int getItemCount() {
        return downloads.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.card_download_name);
            cardView = itemView.findViewById(R.id.card_download);
        }
    }
}
