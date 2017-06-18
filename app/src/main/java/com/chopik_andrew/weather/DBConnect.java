package com.chopik_andrew.weather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Andrew on 18.06.2017.
 */

public class DBConnect {

    private static DBHelper dbHelper;

    private String city;
    private ArrayList<Integer> date;
    private ArrayList<Double> temp;
    private ArrayList<String> desc;
    private ArrayList<Integer> clouds;
    private int count;

    public DBConnect(Context context){

        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("mytable", null, null, null, null, null, null);

        date = new ArrayList<>();
        temp = new ArrayList<>();
        desc = new ArrayList<>();
        clouds = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                date.add(cursor.getInt(cursor.getColumnIndex("date")));
                temp.add(cursor.getDouble(cursor.getColumnIndex("temp")));
                desc.add(cursor.getString(cursor.getColumnIndex("desc")));
                clouds.add(cursor.getInt(cursor.getColumnIndex("clouds")));
            } while (cursor.moveToNext());
            cursor.moveToFirst();
            city = cursor.getString(cursor.getColumnIndex("city"));
            count = cursor.getInt(cursor.getColumnIndex("count"));
        }

        cursor.close();
        dbHelper.close();

    }

    public String getCity() {
        return city;
    }

    public ArrayList<Integer> getDate() {
        return date;
    }

    public ArrayList<Double> getTemp() {
        return temp;
    }

    public ArrayList<String> getDesc() {
        return desc;
    }

    public ArrayList<Integer> getClouds() {
        return clouds;
    }

    public int getCount() {
        return count;
    }


    public ArrayList<ListModel> getList(int count) {
        ArrayList<ListModel> list = new ArrayList<>();

        for(int i = 0; i < count; i++){
            list.add(new ListModel(city, date.get(this.count + i), temp.get(this.count + i), desc.get(this.count + i), clouds.get(this.count + i)));
        }

        return list;
    }
}

class ListModel{
    private String city;
    private int date;
    private double temp;
    private String desc;
    private int clouds;

    public ListModel(String city, int date, double temp, String desc, int clouds){
        this.city = city;
        this.date = date;
        this.temp = temp;
        this.desc = desc;
        this.clouds = clouds;
    }

    public String getCity() {
        return city;
    }

    public int getDate() {
        return date;
    }

    public double getTemp() {
        return temp;
    }

    public String getDesc() {
        return desc;
    }

    public int getClouds() {
        return clouds;
    }
}


