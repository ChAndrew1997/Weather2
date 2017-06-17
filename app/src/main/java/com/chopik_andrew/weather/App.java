package com.chopik_andrew.weather;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.chopik_andrew.weather.weatherApiFiveDays.FiveDaysWeatherAPI;
import com.chopik_andrew.weather.weatherApiFiveDays.FiveDaysWeatherModel;
import com.chopik_andrew.weather.weatherApiSixteenDays.SixteenDaysWeatherAPI;
import com.chopik_andrew.weather.weatherApiSixteenDays.SixteenDaysWeatherModel;

import java.util.ArrayList;

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
    private static SixteenDaysWeatherAPI sixteenDaysWeatherAPI;
    private Retrofit retrofit;
    private static DBHelper dbHelper;

    private static String city;
    private static ArrayList<Integer> date;
    private static ArrayList<Double> temp;
    private static ArrayList<String> desc;
    private static ArrayList<Integer> clouds;


    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/") //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        fiveDaysWeatherAPI = retrofit.create(FiveDaysWeatherAPI.class);
        sixteenDaysWeatherAPI = retrofit.create(SixteenDaysWeatherAPI.class);

    }

    public static void writeDB(Context context){

        dbHelper = new DBHelper(context);
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for(int i = 0; i < date.size(); i++){
            cv.put("city", city);
            cv.put("date", date.get(i));
            cv.put("temp", temp.get(i));
            cv.put("desc", desc.get(i));
            cv.put("clouds", clouds.get(i));
            db.update("mytable", cv, "id = " + Integer.toString(i + 1), null);
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

        cursor.close();
        dbHelper.close();
    }

    public static void downloadWeather(final Context context){

        date = new ArrayList<>();
        temp = new ArrayList<>();
        desc = new ArrayList<>();
        clouds = new ArrayList<>();

        fiveDaysWeatherAPI.getData(55.4, 55.7, "a84d20ba16e63145fec0b712d6547707").enqueue(new Callback<FiveDaysWeatherModel>() {
            @Override
            public void onResponse(Call<FiveDaysWeatherModel> call, Response<FiveDaysWeatherModel> response) {
                city = response.body().getCity().getName();
                for(int i = 0; i < response.body().getList().size(); i++){
                    date.add(response.body().getList().get(i).getDt());
                    temp.add(response.body().getList().get(i).getMain().getTemp());
                    desc.add(response.body().getList().get(i).getWeather().get(0).getMain());
                    clouds.add(response.body().getList().get(i).getClouds().getAll());
                }

                sixteenDaysWeatherAPI.getData(55.4, 55.7, 16, "a84d20ba16e63145fec0b712d6547707").enqueue(new Callback<SixteenDaysWeatherModel>() {
                    @Override
                    public void onResponse(Call<SixteenDaysWeatherModel> call, Response<SixteenDaysWeatherModel> response) {

                        for(int i = 0; i < response.body().getList().size(); i++){
                            date.add(response.body().getList().get(i).getDt());
                            temp.add(response.body().getList().get(i).getTemp().getDay());
                            desc.add(response.body().getList().get(i).getWeather().get(0).getMain());
                            clouds.add(response.body().getList().get(i).getClouds());
                        }

                        writeDB(context);
                    }

                    @Override
                    public void onFailure(Call<SixteenDaysWeatherModel> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            @Override
            public void onFailure(Call<FiveDaysWeatherModel> call, Throwable t) {

            }
        });
    }
}
