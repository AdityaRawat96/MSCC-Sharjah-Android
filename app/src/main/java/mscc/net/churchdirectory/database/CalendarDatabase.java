package mscc.net.churchdirectory.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import mscc.net.churchdirectory.model.Calendar;


public class CalendarDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="calendarDatabase";

    private static final String TABLE_MONTHS="months";


    private static final String KEY_ID="id";
    private static final String KEY_MONTH="month";
    private static final String KEY_TIMESTAMP="timestamp";

    public CalendarDatabase(Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_COINS_TABLE = "CREATE TABLE " + TABLE_MONTHS + "("
                + KEY_ID + " TEXT PRIMARY KEY,"
                + KEY_MONTH + " TEXT,"
                + KEY_TIMESTAMP + " TEXT" + ")";

        db.execSQL(CREATE_COINS_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_MONTHS);
        //Create Table Again
        onCreate(db);

    }
    public void addItem(Calendar calendar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, calendar.monthId);
        values.put(KEY_MONTH, calendar.monthName);
        values.put(KEY_TIMESTAMP, calendar.monthTimestamp);
        // INSERTING ROW
        db.insert(TABLE_MONTHS, null, values);
        db.close(); // CLOSING DATABASE CONNECTION
    }


    // GET ALL DATA FROM DATABASE
    public List<Calendar> getAllMonths() {
        List<Calendar> monthList = new ArrayList<>();

        // 1. BUILD THE QUERY
        String query = "SELECT * FROM " + TABLE_MONTHS;

        // 2. GET REFERENCE TO WRITABLE DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. GO OVER EACH ROW, BUILD FUN FACTS AND ADD IT TO LIST
        Calendar calendar;
        if(cursor!=null && cursor.getCount() > 0)
        {
            if (cursor.moveToFirst()) {
                do {
                    calendar = new Calendar(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2)
                    );
                    monthList.add(calendar);
                } while (cursor.moveToNext());
            }}

        // RETURN FACT
        return monthList;
    }

    public void deleteRecords(Calendar calendar) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MONTHS, KEY_ID + " =? ", new String[]{String.valueOf(calendar.monthId)});
        db.close();
    }
}
