package mscc.net.churchdirectory.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

import mscc.net.churchdirectory.MainActivity;
import mscc.net.churchdirectory.R;

import mscc.net.churchdirectory.model.Articles;

public class ArticleActivity extends AppCompatActivity {


    public static final String ARTICLE_DATA = "parcelable_article_data";
    private Articles.Article article;

    private Toolbar toolbar;
    private ImageView image;
    private TextView title;
    private TextView content;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        toolbar = findViewById(R.id.toolbar);
        image = findViewById(R.id.article_image);
        title = findViewById(R.id.article_title);
        content = findViewById(R.id.article_content);
        button = findViewById(R.id.readMoreButton);

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

        if(article.getLink().length() > 4){
            button.setVisibility(View.VISIBLE);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArticleActivity.this, WebViewActivity.class);
                intent.putExtra("urlString", article.getLink());
                startActivity(intent);
            }
        });
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
