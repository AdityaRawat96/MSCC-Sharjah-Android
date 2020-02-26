package mscc.net.churchdirectory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import mscc.net.churchdirectory.model.CommitteeMember;

import java.util.ArrayList;
import java.util.List;

public class CommitteeMemberDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="committeeMemberDatabase";

    private static final String TABLE_COMMITTEEMEMBER = "committeeMember";


    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String KEY_IMAGE="image";
    private static final String KEY_DESIGNATION="designation";
    private static final String KEY_MEMBEROF="memberOf";
    private static final String KEY_TIMESTAMP="timestamp";
    private static final String KEY_RANK="rank";
    private static final String KEY_EXECUTIVE="executive";

    public CommitteeMemberDatabase(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_COMMITTEEMEMBER_TABLE = "CREATE TABLE " + TABLE_COMMITTEEMEMBER + "("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_DESIGNATION + " TEXT,"
                + KEY_MEMBEROF + " TEXT,"
                + KEY_TIMESTAMP + " TEXT,"
                + KEY_RANK + " TEXT,"
                + KEY_EXECUTIVE + " TEXT" + ")";

        db.execSQL(CREATE_COMMITTEEMEMBER_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_COMMITTEEMEMBER);
        //Create Table Again
        onCreate(db);

    }
    public void addItem(CommitteeMember committeeMember) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, committeeMember.committeeMemberId);
        values.put(KEY_NAME, committeeMember.committeeMemberName);
        values.put(KEY_IMAGE, committeeMember.committeeMemberImage);
        values.put(KEY_DESIGNATION, committeeMember.committeeMemberDesignation);
        values.put(KEY_MEMBEROF, committeeMember.committeeMemberMemberOf);
        values.put(KEY_TIMESTAMP, committeeMember.committeeMemberTimestamp);
        values.put(KEY_RANK, committeeMember.committeeMemberRank);
        values.put(KEY_EXECUTIVE, committeeMember.executiveMember);

        // INSERTING ROW
        db.insert(TABLE_COMMITTEEMEMBER, null, values);
        db.close(); // CLOSING DATABASE CONNECTION
    }


    // GET ALL DATA FROM DATABASE
    public List<CommitteeMember> getAllCommitteeMembers(String committeeId) {
        List<CommitteeMember> committeeMemberList = new ArrayList<>();

        // 1. BUILD THE QUERY
        String query = "SELECT * FROM " + TABLE_COMMITTEEMEMBER + " WHERE " + KEY_MEMBEROF + " = " + committeeId;

        // 2. GET REFERENCE TO WRITABLE DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. GO OVER EACH ROW, BUILD FUN FACTS AND ADD IT TO LIST
        CommitteeMember committeeMember;
        if(cursor!=null && cursor.getCount() > 0)
        {
            if (cursor.moveToFirst()) {
                do {
                    committeeMember = new CommitteeMember(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7)
                    );
                    committeeMemberList.add(committeeMember);
                } while (cursor.moveToNext());
            }}

        // RETURN FACT
        return committeeMemberList;
    }

    public void deleteRecords(CommitteeMember committeeMember) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMMITTEEMEMBER, KEY_ID + " =? ", new String[]{String.valueOf(committeeMember.committeeMemberId)});
        db.close();
    }

    public void deleteAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMMITTEEMEMBER, null, null);
        db.close();
    }
}
