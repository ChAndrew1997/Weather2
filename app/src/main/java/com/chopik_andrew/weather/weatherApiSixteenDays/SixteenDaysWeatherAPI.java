package com.chopik_andrew.weather.weatherApiSixteenDays;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Andrew on 17.06.2017.
 */

public interface SixteenDaysWeatherAPI {
    @GET("/data/2.5/forecast/daily")
    Call<SixteenDaysWeatherModel> getData(@Query("lat") double latitude, @Query("lon") double longitude,
                                          @Query("cnt") int count, @Query("APPID") String key);
}
