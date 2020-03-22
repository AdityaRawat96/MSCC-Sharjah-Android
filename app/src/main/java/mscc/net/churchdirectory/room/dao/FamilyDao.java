package mscc.net.churchdirectory.room.dao;



import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import mscc.net.churchdirectory.room.model.Family;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Dany on 18-02-2018.
 */

@Dao
public interface FamilyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Family family);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Family> families);

    @Delete
    int deleteAll(Family[] families);

    @Query("DELETE FROM family")
    void nukeTable();

    @Update
    int update(Family family);

    @Query("SELECT * FROM family WHERE id LIKE :id ")
    Flowable<Family> getFamily(int id);

    @Query("SELECT * FROM  family WHERE visible = 1 ORDER BY id")
    LiveData<List<Family>> getFamilies();

    @Query("SELECT * FROM  family WHERE visible = 1 ORDER BY id")
    Flowable<List<Family>> getFlowableFamilies();

}
