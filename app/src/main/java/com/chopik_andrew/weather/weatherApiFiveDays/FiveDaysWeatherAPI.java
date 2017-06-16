package com.chopik_andrew.weather.weatherApiFiveDays;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Andrew on 16.06.2017.
 */

public interface FiveDaysWeatherAPI {
    @GET("/data/2.5/forecast")
    Call<FiveDaysWeatherModel> getData(@Query("lat") double latitude, @Query("lon") double longitude, @Query("APPID") String key);
}
