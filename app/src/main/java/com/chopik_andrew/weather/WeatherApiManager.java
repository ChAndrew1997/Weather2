package com.chopik_andrew.weather;

import android.content.Context;
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
 * Created by Andrew on 19.06.2017.
 */

public class WeatherApiManager {

    private static WeatherApiManager mInstance;

    public static final String BASE_URL = "http://api.openweathermap.org/";

    private FiveDaysWeatherAPI fiveDaysWeatherAPI;
    private SixteenDaysWeatherAPI sixteenDaysWeatherAPI;
    private Retrofit retrofit;

    private Context mContext;

    private String city;
    private ArrayList<Integer> date;
    private ArrayList<Double> temp;
    private ArrayList<String> desc;
    private ArrayList<Integer> clouds;
    private int count;

    private LodWeatherListener mLodWeatherListener;

    public static synchronized WeatherApiManager getInstance() {
        if (mInstance == null) {
            mInstance = new WeatherApiManager();
        }

        return mInstance;
    }

    private WeatherApiManager() {

    }

    public void init(Context context) {
        mContext = context;

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер, необходимый для преобразования JSON'а в объекты
                .build();
        fiveDaysWeatherAPI = retrofit.create(FiveDaysWeatherAPI.class);
        sixteenDaysWeatherAPI = retrofit.create(SixteenDaysWeatherAPI.class);
    }

    public void downloadWeather(final Context context, final double lat, final double lon, final LodWeatherListener listener) {

        date = new ArrayList<>();
        temp = new ArrayList<>();
        desc = new ArrayList<>();
        clouds = new ArrayList<>();

        if (listener != null) {
            listener.start();
        }

        fiveDaysWeatherAPI.getData(lat, lon, "a84d20ba16e63145fec0b712d6547707").enqueue(new Callback<FiveDaysWeatherModel>() {
            @Override
            public void onResponse(Call<FiveDaysWeatherModel> call, Response<FiveDaysWeatherModel> response) {
                city = response.body().getCity().getName();
                count = response.body().getCnt();
                for (int i = 0; i < response.body().getList().size(); i++) {
                    date.add(response.body().getList().get(i).getDt());
                    temp.add(response.body().getList().get(i).getMain().getTemp());
                    desc.add(response.body().getList().get(i).getWeather().get(0).getMain());
                    clouds.add(response.body().getList().get(i).getClouds().getAll());
                }

                sixteenDaysWeatherAPI.getData(lat, lon, 16, "a84d20ba16e63145fec0b712d6547707").enqueue(new Callback<SixteenDaysWeatherModel>() {
                    @Override
                    public void onResponse(Call<SixteenDaysWeatherModel> call, Response<SixteenDaysWeatherModel> response) {

                        for (int i = 0; i < response.body().getList().size(); i++) {
                            date.add(response.body().getList().get(i).getDt());
                            temp.add(response.body().getList().get(i).getTemp().getDay());
                            desc.add(response.body().getList().get(i).getWeather().get(0).getMain());
                            clouds.add(response.body().getList().get(i).getClouds());
                        }

//                        app.writeDB(context);

                        if (listener != null) {
                            listener.success();
                        }
                    }

                    @Override
                    public void onFailure(Call<SixteenDaysWeatherModel> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                        if (listener != null) {
                            listener.failure();
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<FiveDaysWeatherModel> call, Throwable t) {
                if (listener != null) {
                    listener.failure();
                }
            }
        });
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

    public interface LodWeatherListener {
        void start();

        void success();

        void failure();
    }
}
