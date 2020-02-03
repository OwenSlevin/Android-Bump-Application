package com.example.owen_.software_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "tasksdb";

    // Table name
    private static final String TABLE_NAME = "tasks";


    // Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ANDROID_ID = "androidid";
    private static final String KEY_MEDIA_TYPE = "type";
    private static final String KEY_USER_NAME = "username";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LONG = "longitude";
    private static final String KEY_RADIUS = "radius";

    //constructor for the class
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //method called when the database needs to be created
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_APPOINTMENT_TABLE = "CREATE TABLE " + TABLE_NAME +
                "(" + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_ANDROID_ID + " TEXT, "
                + KEY_MEDIA_TYPE + " TEXT, "
                + KEY_USER_NAME + " TEXT, "
                + KEY_LAT + " DOUBLE, "
                + KEY_LONG + " DOUBLE, "
                + KEY_RADIUS + " DOUBLE" + ")";
        sqLiteDatabase.execSQL(CREATE_APPOINTMENT_TABLE);

    }

    //method called when the data base needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //method called whenadding items to the database
    public void addItem(Items items) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ANDROID_ID, items.getAndroidID());
        contentValues.put(KEY_MEDIA_TYPE, items.getMediaType());
        contentValues.put(KEY_USER_NAME, items.getUserName());
        contentValues.put(KEY_LAT, items.getLatitude());
        contentValues.put(KEY_LONG, items.getLongitude());
        contentValues.put(KEY_RADIUS, items.getRadius());

        //Was supposed to prevent duplicate SQLite insertions
        db.insertWithOnConflict(TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public Items getItem(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID, KEY_USER_NAME}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Items myItems = new Items(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                Double.parseDouble(cursor.getString(4)),
                Double.parseDouble(cursor.getString(5)),
                Double.parseDouble(cursor.getString(6)));
        cursor.close();
        return myItems;
    }



    public List<Items> getAllItems() throws ParseException {
        List<Items> itemsList = new ArrayList<Items>();
        // Select All Queries
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                Items items = new Items();
                items.setId(Integer.parseInt(cursor.getString(0)));
                items.setAndroidID(cursor.getString(1));
                items.setMediaType(cursor.getString(2));
                items.setUserName(cursor.getString(3));
                items.setLatitude(Double.parseDouble(cursor.getString(4)));
                items.setLongitude(Double.parseDouble(cursor.getString(5)));
                items.setRadius(Double.parseDouble(cursor.getString(6)));
                itemsList.add(items);

            } while (cursor.moveToNext());
        }
        cursor.close();
        // return list
        return itemsList;
    }

    // Getting Count
    public int getItemCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating a single item
    public void updateItem(Items items) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USER_NAME, items.getAndroidID());
        values.put(KEY_USER_NAME, items.getMediaType());
        values.put(KEY_USER_NAME, items.getUserName());
        values.put(KEY_USER_NAME, items.getLatitude());
        values.put(KEY_USER_NAME, items.getLongitude());
        values.put(KEY_USER_NAME, items.getRadius());

        // updating row
        db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[]{String.valueOf(items.getId())});
        db.close();
    }

    // Deleting a single item
    public void deleteItem(Items items) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?",
                new String[]{String.valueOf(items.getId())});
        db.close();
    }
}
