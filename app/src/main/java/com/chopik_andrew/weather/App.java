package com.chopik_andrew.weather;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chopik_andrew.weather.weatherApiFiveDays.FiveDaysWeatherAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Andrew on 16.06.2017.
 */

public class App extends Application {
    private static FiveDaysWeatherAPI fiveDaysWeatherAPI;
    private Retrofit retrofit;
    private static DBHelper dbHelper;


    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        fiveDaysWeatherAPI = retrofit.create(FiveDaysWeatherAPI.class); //Создаем объект, при помощи которого будем выполнять запросы
    }

    public static FiveDaysWeatherAPI getApi() {
        return fiveDaysWeatherAPI;
    }

    public static void writeDB(Context context){

        dbHelper = new DBHelper(context);
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for(int i = 0; i < 10; i++){
            cv.put("city", "Minsk");
            cv.put("date", 1221 + i);
            cv.put("temp", 25);
            cv.put("desc", "frost");
            cv.put("clouds", 54);
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
                        ", clouds = " + cursor.getDouble(cursor.getColumnIndex("clouds"))
                );
            } while (cursor.moveToNext());
        }

        Log.d("table", "deleted rows count = " + db.delete("mytable", null, null));

        cursor.close();
        dbHelper.close();
    }
}
