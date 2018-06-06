package com.example.galileo.openweatherapp.module.object;

import android.content.Context;
import android.util.Log;

import com.example.galileo.openweatherapp.data.dao.InformationDao;
import com.example.galileo.openweatherapp.retrofit.RestClient;
import com.example.galileo.openweatherapp.retrofit.WebService;
import com.example.galileo.openweatherapp.data.ResWeather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetWeatherForecastObject extends InformationDao{

    Context context;
    WebService webService;

    OnMessageSuccess onMessageSuccess;


    static GetWeatherForecastObject getWeatherForecastObject = null;

    public synchronized static GetWeatherForecastObject getInstance(Context context) {
        if(getWeatherForecastObject == null) {
            getWeatherForecastObject = new GetWeatherForecastObject(context);
        } return getWeatherForecastObject;
    }

    public void setOnSuccess(OnMessageSuccess onMessageSuccess) {
        this.onMessageSuccess = onMessageSuccess;
    }

    public interface OnMessageSuccess {
        public void onMessageSuccess(boolean isSuccess, ResWeather response);
    }

    public GetWeatherForecastObject(Context context) {
        super(context);
        this.context = context;
        webService = RestClient.getInstance().getWebService();
    }

    public void getWeatherForecast(){
        webService.getWeatherUpdate().enqueue(new Callback<ResWeather>() {

            @Override
            public void onResponse(Call<ResWeather> call,
                                   final Response<ResWeather> response) {

                if (response.isSuccessful()) {
                    if(response.body() != null) {
                        insertInformations(response.body().getList());
                        updateUi(true, response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResWeather> call, Throwable t) {
                Log.e("----getMessage", "fail "+t.toString());
            }
        });
    }

    public void updateUi(boolean isSuccess, ResWeather response){
        if(onMessageSuccess != null) onMessageSuccess.onMessageSuccess(isSuccess, response);
    }
}
