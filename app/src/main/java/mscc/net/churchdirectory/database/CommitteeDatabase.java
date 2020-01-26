package mscc.net.churchdirectory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import mscc.net.churchdirectory.model.Committee;

public class CommitteeDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="committeeDatabase";

    private static final String TABLE_COMMITTEE = "committee";


    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String KEY_IMAGE="image";
    private static final String KEY_TIMESTAMP="timestamp";
    private static final String KEY_RANK="rank";

    public CommitteeDatabase(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_COMMITTEE_TABLE = "CREATE TABLE " + TABLE_COMMITTEE + "("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_TIMESTAMP + " TEXT,"
                + KEY_RANK + " TEXT" + ")";

        db.execSQL(CREATE_COMMITTEE_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_COMMITTEE);
        //Create Table Again
        onCreate(db);

    }
    public void addItem(Committee committee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, committee.committeeId);
        values.put(KEY_NAME, committee.committeeName);
        values.put(KEY_IMAGE, committee.committeeImage);
        values.put(KEY_TIMESTAMP, committee.committeeTimestamp);
        values.put(KEY_RANK, committee.committeeRank);
        // INSERTING ROW
        db.insert(TABLE_COMMITTEE, null, values);
        db.close(); // CLOSING DATABASE CONNECTION
    }


    // GET ALL DATA FROM DATABASE
    public List<Committee> getAllCommittees() {
        List<Committee> committeeList = new ArrayList<>();

        // 1. BUILD THE QUERY
        String query = "SELECT * FROM " + TABLE_COMMITTEE;

        // 2. GET REFERENCE TO WRITABLE DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. GO OVER EACH ROW, BUILD FUN FACTS AND ADD IT TO LIST
        Committee committee;
        if(cursor!=null && cursor.getCount() > 0)
        {
            if (cursor.moveToFirst()) {
                do {
                    committee = new Committee(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4)
                    );
                    committeeList.add(committee);
                } while (cursor.moveToNext());
            }}

        // RETURN FACT
        return committeeList;
    }

    public void deleteRecords(Committee committee) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMMITTEE, KEY_ID + " =? ", new String[]{String.valueOf(committee.committeeId)});
        db.close();
    }

    public void deleteAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMMITTEE, null, null);
        db.close();
    }
}
