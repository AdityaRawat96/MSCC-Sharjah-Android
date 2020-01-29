package mscc.net.churchdirectory.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mscc.net.churchdirectory.ui.activity.ArticleActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import mscc.net.churchdirectory.R;

import mscc.net.churchdirectory.model.Articles;

/**
 * Created by Dany on 02-01-2018.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    private List<Articles.Article> articles;
    private Context context;

    public ArticlesAdapter(List<Articles.Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_article, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(articles.get(position).getTitle());
        holder.snippet.setText(articles.get(position).getContent());
        Picasso.get().load(articles.get(position).getImage())
                .error(R.drawable.ic_placeholder_background)
                .placeholder(R.drawable.ic_placeholder_background)
                .into(holder.image);

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ArticleActivity.class);
            intent.putExtra(ArticleActivity.ARTICLE_DATA, articles.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        ImageView image;
        TextView title;
        TextView snippet;

        ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_article);
            image = itemView.findViewById(R.id.card_article_image);
            title = itemView.findViewById(R.id.card_article_name);
            snippet = itemView.findViewById(R.id.card_article_snippet);
        }
    }
}
