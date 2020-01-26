package mscc.net.churchdirectory.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mscc.net.churchdirectory.R;

import mscc.net.churchdirectory.model.Articles;

public class ArticleActivity extends AppCompatActivity {


    public static final String ARTICLE_DATA = "parcelable_article_data";
    private Articles.Article article;

    private android.support.v7.widget.Toolbar toolbar;
    private ImageView image;
    private TextView title;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        toolbar = findViewById(R.id.toolbar);
        image = findViewById(R.id.article_image);
        title = findViewById(R.id.article_title);
        content = findViewById(R.id.article_content);

        article = getIntent().getParcelableExtra(ARTICLE_DATA);

        if (article == null) {
            finish();
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        title.setText(article.getTitle());
        content.setText(article.getContent());
        Picasso.get().load(article.getImage())
                .error(R.drawable.ic_placeholder_background)
                .placeholder(R.drawable.ic_placeholder_background)
                .into(image);
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
