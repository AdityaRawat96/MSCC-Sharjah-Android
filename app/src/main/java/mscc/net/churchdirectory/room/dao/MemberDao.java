package mscc.net.churchdirectory.room.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import mscc.net.churchdirectory.room.model.Member;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by Dany on 18-02-2018.
 */

@Dao
public interface MemberDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Member member);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Member> members);

    @Delete
    int deleteAll(Member[] members);

    @Delete
    int delete(Member members);

    @Query("DELETE FROM member WHERE familyId LIKE :familyId")
    void deleteMembers(int familyId);

    @Query("DELETE FROM member")
    void nukeTable();

    @Update
    int update(Member member);

    @Query("SELECT * FROM member WHERE familyId LIKE :familyId")
    Flowable<List<Member>> getMembers(int familyId);

    @Query("SELECT * FROM member WHERE id LIKE :id")
    Flowable<Member> getMember(int id);

    @Query("SELECT * FROM  member ORDER BY id")
    LiveData<List<Member>> getMembers();

    @Query("SELECT * FROM  member ORDER BY id")
    Flowable<List<Member>> getFlowableMembers();

}
