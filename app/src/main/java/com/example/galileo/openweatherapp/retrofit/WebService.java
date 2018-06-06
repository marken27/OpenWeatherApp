package com.example.galileo.openweatherapp.retrofit;

import com.example.galileo.openweatherapp.data.ResWeather;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.example.galileo.openweatherapp.utility.Constants.WEATHER_ENDPOINT;


public interface WebService {

    @GET(WEATHER_ENDPOINT)
    Call<ResWeather> getWeatherUpdate();

}
