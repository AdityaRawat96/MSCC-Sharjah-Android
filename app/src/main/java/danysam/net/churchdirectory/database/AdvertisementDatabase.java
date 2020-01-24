package danysam.net.churchdirectory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import danysam.net.churchdirectory.model.Advertisement;

public class AdvertisementDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=2;
    private static final String DATABASE_NAME="advertisementDatabase";

    private static final String TABLE_ADVERTISEMENT = "advertisement";


    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String KEY_DESCRIPTION="description";
    private static final String KEY_IMAGE_BANNER="bannerImage";
    private static final String KEY_IMAGE_FULL="fullImage";
    private static final String KEY_WEBSITE="website";
    private static final String KEY_PHONE="phone";
    private static final String KEY_TIMESTAMP="timestamp";

    public AdvertisementDatabase(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_METROPOLITIAN_TABLE = "CREATE TABLE " + TABLE_ADVERTISEMENT + "("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_IMAGE_BANNER + " TEXT,"
                + KEY_IMAGE_FULL + " TEXT,"
                + KEY_WEBSITE + " TEXT,"
                + KEY_PHONE + " TEXT,"
                + KEY_TIMESTAMP + " TEXT" +")";

        db.execSQL(CREATE_METROPOLITIAN_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_ADVERTISEMENT);
        //Create Table Again
        onCreate(db);

    }
    public void addItem(Advertisement advertisement) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, advertisement.advertisementId);
        values.put(KEY_NAME, advertisement.advertisementName);
        values.put(KEY_DESCRIPTION, advertisement.advertisementDescription);
        values.put(KEY_IMAGE_BANNER, advertisement.advertisementImageBanner);
        values.put(KEY_IMAGE_FULL, advertisement.advertisementImageFull);
        values.put(KEY_WEBSITE, advertisement.advertisementWebsite);
        values.put(KEY_PHONE, advertisement.advertisementPhone);
        values.put(KEY_TIMESTAMP, advertisement.advertisementTimestamp);
        // INSERTING ROW
        db.insert(TABLE_ADVERTISEMENT, null, values);
        db.close(); // CLOSING DATABASE CONNECTION
    }


    // GET ALL DATA FROM DATABASE
    public List<Advertisement> getAllAdvertisements() {
        List<Advertisement> advertisementList = new ArrayList<>();

        // 1. BUILD THE QUERY
        String query = "SELECT * FROM " + TABLE_ADVERTISEMENT;

        // 2. GET REFERENCE TO WRITABLE DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. GO OVER EACH ROW, BUILD FUN FACTS AND ADD IT TO LIST
        Advertisement advertisement;
        if(cursor!=null && cursor.getCount() > 0)
        {
            if (cursor.moveToFirst()) {
                do {
                    advertisement = new Advertisement(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7)
                    );
                    advertisementList.add(advertisement);
                } while (cursor.moveToNext());
            }}

        // RETURN FACT
        return advertisementList;
    }

    public void deleteRecords(Advertisement advertisement) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ADVERTISEMENT, KEY_ID + " =? ", new String[]{String.valueOf(advertisement.advertisementId)});
        db.close();
    }

    public void deleteAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ADVERTISEMENT, null, null);
        db.close();
    }
}
