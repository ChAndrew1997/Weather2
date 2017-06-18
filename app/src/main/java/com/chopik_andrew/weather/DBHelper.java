package com.chopik_andrew.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Andrew on 17.06.2017.
 */

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(Context context) {
        super(context, "myDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "city text,"
                + "date integer,"
                + "temp real,"
                + "desc text,"
                + "clouds real,"
                + "count integer"
                + ");");

        ContentValues cv = new ContentValues();

        for(int i = 0; i < 60; i++){
            cv.put("city", "Update Page");
            cv.put("date", 1221 + i);
            cv.put("temp", 25);
            cv.put("desc", "frost");
            cv.put("clouds", 54);
            cv.put("count", 40);
            db.insert("mytable", null, cv);
            cv.clear();
        }

        Cursor cursor = db.query("mytable", null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do{
                Log.d("table", "ID = " + cursor.getInt(cursor.getColumnIndex("id")) +
                        ", city = " + cursor.getString(cursor.getColumnIndex("city")) +
                        ", date = " + cursor.getInt(cursor.getColumnIndex("date")) +
                        ", temp = " + cursor.getDouble(cursor.getColumnIndex("temp")) +
                        ", desc = " + cursor.getString(cursor.getColumnIndex("desc")) +
                        ", clouds = " + cursor.getDouble(cursor.getColumnIndex("clouds")) +
                        ", count = " + cursor.getInt(cursor.getColumnIndex("count"))
                );
            } while (cursor.moveToNext());
        }

        cursor.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
