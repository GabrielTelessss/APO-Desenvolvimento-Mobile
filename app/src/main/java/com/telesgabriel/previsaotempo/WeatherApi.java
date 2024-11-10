package com.telesgabriel.previsaotempo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("weather")
    Call<WeatherResponse> getWeather(@Query("key") String apiKey, @Query("city") String city);
}