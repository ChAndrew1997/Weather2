package com.chopik_andrew.weather;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.chopik_andrew.weather.weatherApiFiveDays.FiveDaysWeatherAPI;
import com.chopik_andrew.weather.weatherApiFiveDays.FiveDaysWeatherModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Andrew on 16.06.2017.
 */

public class App extends Application {
    private static FiveDaysWeatherAPI fiveDaysWeatherAPI;
    private Retrofit retrofit;
    private static DBHelper dbHelper;

    private String city;
    private int date;
    private double temp;
    private String desc;
    private double clouds;


    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        fiveDaysWeatherAPI = retrofit.create(FiveDaysWeatherAPI.class); //Создаем объект, при помощи которого будем выполнять запросы

    }

    public static void writeDB(Context context){

        dbHelper = new DBHelper(context);
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

       /* for(int i = 0; i < 10; i++){
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

        cursor.close();*/
        dbHelper.close();
    }

    private void getApi(){

        fiveDaysWeatherAPI.getData(55.4, 55.7, "a84d20ba16e63145fec0b712d6547707").enqueue(new Callback<FiveDaysWeatherModel>() {
            @Override
            public void onResponse(Call<FiveDaysWeatherModel> call, Response<FiveDaysWeatherModel> response) {
            }

            @Override
            public void onFailure(Call<FiveDaysWeatherModel> call, Throwable t) {
            }
        });

    }

}
