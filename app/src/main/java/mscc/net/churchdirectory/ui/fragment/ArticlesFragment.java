package mscc.net.churchdirectory.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mscc.net.churchdirectory.room.MainDatabase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mscc.net.churchdirectory.R;
import mscc.net.churchdirectory.adapter.ArticlesAdapter;
import mscc.net.churchdirectory.connection.ConnectionManager;
import mscc.net.churchdirectory.model.Articles;
import mscc.net.churchdirectory.room.dao.ArticleDao;
import mscc.net.churchdirectory.room.model.Article;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by Dany on 02-01-2018.
 */

public class ArticlesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArticlesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Articles.Article> articles;
    private MainDatabase db;
    private ArticleDao articleDao;
    private Flowable<List<Article>> flowableArticles;

    private Observable<Response<Articles>> getArticles() {
        return ConnectionManager
                .getInstance()
                .getClient()
                .getArticles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_articles, container, false);

        db = MainDatabase.getInstance(getActivity());
        articleDao = db.getArticleDao();

        articles = new ArrayList<>();

        recyclerView = view.findViewById(R.id.rv_articles);

        adapter = new ArticlesAdapter(articles, getActivity());
        layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        flowableArticles = articleDao.getFlowableArticles();

        flowableArticles
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onDataUpdated, this::onDataError);


        getArticles().subscribe(this::setArticles, this::setError);

        return view;
    }

    private void onDataError(Throwable throwable) {
        Log.e("RoomDB Error", throwable.getLocalizedMessage());
    }

    private void onDataUpdated(List<Article> articleList) {
        articles.clear();
        articles.addAll(dbToList(articleList));
        // adapter.notifyDataSetChanged();
        getActivity().runOnUiThread(() -> adapter.notifyDataSetChanged());
    }

    private Collection<? extends Articles.Article> dbToList(List<Article> articleList) {

        List<Articles.Article> articles = new ArrayList<>();
        for (Article a : articleList) {
            articles.add(new Articles.Article(String.valueOf(a.getId()), a.getTitle(), a.getContent(), a.getImage(), a.getDate()));
        }
        return articles;
    }

    private void setError(Throwable throwable) {
        Log.e("RX Network Error", throwable.getLocalizedMessage());
    }

    private void setArticles(Response<Articles> articlesResponse) {
        if (articlesResponse.isSuccessful()) {
            responseToDb(articlesResponse.body().getResponse().getArticles());
        }
    }

    private void responseToDb(List<Articles.Article> articles) {

        List<Article> articleList = new ArrayList<>();

        for (Articles.Article a : articles) {
            articleList.add(new Article(Integer.parseInt(a.getId()), a.getTitle(), a.getContent(), a.getImage(), a.getDate()));
        }

        Observable.just(db)
                .subscribeOn(Schedulers.io())
                .subscribe(mainDatabase -> {
                    articleDao.nukeTable();
                    articleDao.insertAll(articleList);
                }, err -> Log.e("RoomErrorMembers", err.getMessage()));

    }

}
