package mscc.net.churchdirectory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import mscc.net.churchdirectory.model.Metropolitian;


public class MetropolitianDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="metropolitianDatabase";

    private static final String TABLE_METROPOLITIAN = "metropolitian";


    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String KEY_DESIGNATION="designation";
    private static final String KEY_RANK="rank";
    private static final String KEY_IMAGE="image";
    private static final String KEY_TIMESTAMP="timestamp";

    public MetropolitianDatabase(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_METROPOLITIAN_TABLE = "CREATE TABLE " + TABLE_METROPOLITIAN + "("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_DESIGNATION + " TEXT,"
                + KEY_RANK + " TEXT,"
                + KEY_IMAGE + " TEXT,"
                + KEY_TIMESTAMP + " TEXT" + ")";

        db.execSQL(CREATE_METROPOLITIAN_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_METROPOLITIAN);
        //Create Table Again
        onCreate(db);

    }
    public void addItem(Metropolitian metropolitian) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, metropolitian.metropolitianId);
        values.put(KEY_NAME, metropolitian.metropolitianName);
        values.put(KEY_DESIGNATION, metropolitian.metropolitianDesignation);
        values.put(KEY_RANK, metropolitian.metropolitianRank);
        values.put(KEY_IMAGE, metropolitian.metropolitianImage);
        values.put(KEY_TIMESTAMP, metropolitian.metropolitianTimestamp);
        // INSERTING ROW
        db.insert(TABLE_METROPOLITIAN, null, values);
        db.close(); // CLOSING DATABASE CONNECTION
    }


    // GET ALL DATA FROM DATABASE
    public List<Metropolitian> getAllMetropolitians() {
        List<Metropolitian> metropolitianList = new ArrayList<>();

        // 1. BUILD THE QUERY
        String query = "SELECT * FROM " + TABLE_METROPOLITIAN;

        // 2. GET REFERENCE TO WRITABLE DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. GO OVER EACH ROW, BUILD FUN FACTS AND ADD IT TO LIST
        Metropolitian metropolitian;
        if(cursor!=null && cursor.getCount() > 0)
        {
            if (cursor.moveToFirst()) {
                do {
                    metropolitian = new Metropolitian(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5)
                    );
                    metropolitianList.add(metropolitian);
                } while (cursor.moveToNext());
            }}

        // RETURN FACT
        return metropolitianList;
    }

    public void deleteRecords(Metropolitian metropolitian) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_METROPOLITIAN, KEY_ID + " =? ", new String[]{String.valueOf(metropolitian.metropolitianId)});
        db.close();
    }

    public void deleteAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_METROPOLITIAN, null, null);
        db.close();
    }
}
