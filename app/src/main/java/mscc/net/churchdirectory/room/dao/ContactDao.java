package mscc.net.churchdirectory.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import mscc.net.churchdirectory.room.model.Contact;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Dany on 18-02-2018.
 */

@Dao
public interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contact contact);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Contact> contacts);

    @Delete
    int deleteAll(Contact[] contacts);

    @Query("DELETE FROM contact")
    void nukeTable();

    @Update
    int update(Contact contact);

    @Query("SELECT * FROM contact WHERE familyId LIKE :familyId")
    Flowable<List<Contact>> getContacts(int familyId);

    @Query("SELECT * FROM  contact ORDER BY name")
    LiveData<List<Contact>> getContacts();

    @Query("SELECT * FROM  contact ORDER BY name")
    Flowable<List<Contact>> getFlowableContacts();

    @Query("DELETE FROM contact WHERE familyId LIKE :familyId")
    void deleteContacts(int familyId);
}
