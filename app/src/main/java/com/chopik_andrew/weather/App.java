package com.chopik_andrew.weather;

import android.app.Application;

import com.chopik_andrew.weather.weatherApiFiveDays.FiveDaysWeatherAPI;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Andrew on 16.06.2017.
 */

public class App extends Application {
    private static FiveDaysWeatherAPI fiveDaysWeatherAPI;
    private Retrofit retrofit;

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
}
