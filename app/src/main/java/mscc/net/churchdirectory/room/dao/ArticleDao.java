package mscc.net.churchdirectory.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import mscc.net.churchdirectory.room.model.Article;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Dany on 11-03-2018.
 */

@Dao
public interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Article article);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Article> articles);

    @Delete
    int deleteAll(Article[] articles);

    @Update
    int update(Article article);

    @Query("DELETE FROM article")
    void nukeTable();

    @Query("SELECT * FROM article WHERE id LIKE :id ORDER BY id DESC")
    Flowable<List<Article>> getArticles(int id);

    @Query("SELECT * FROM  article ORDER BY id DESC")
    LiveData<List<Article>> getArticles();

    @Query("SELECT * FROM  article ORDER BY id DESC")
    Flowable<List<Article>> getFlowableArticles();

}
