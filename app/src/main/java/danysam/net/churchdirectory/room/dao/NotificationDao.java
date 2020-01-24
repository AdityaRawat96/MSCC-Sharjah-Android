package danysam.net.churchdirectory.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import danysam.net.churchdirectory.room.model.Notification;
import io.reactivex.Flowable;

/**
 * Created by Dany on 11-03-2018.
 */

@Dao
public interface NotificationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Notification notification);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Notification> notifications);

    @Delete
    int delete(Notification[] notifications);

    @Query("DELETE FROM notification")
    void nukeTable();

    @Update
    int update(Notification notification);

    @Query("SELECT * FROM notification WHERE id LIKE :id ORDER BY id DESC")
    Flowable<List<Notification>> getNotification(int id);

    @Query("SELECT * FROM  notification ORDER BY id DESC")
    LiveData<List<Notification>> getNotification();

    @Query("SELECT * FROM  notification ORDER BY id DESC")
    List<Notification> getNews();

    @Query("SELECT * FROM  notification ORDER BY id DESC")
    Flowable<List<Notification>> getFlowableNotifications();
}
