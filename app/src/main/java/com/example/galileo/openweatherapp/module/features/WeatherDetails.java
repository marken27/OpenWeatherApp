package com.example.galileo.openweatherapp.module.features;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.example.galileo.openweatherapp.R;
import com.example.galileo.openweatherapp.app.Application;
import com.example.galileo.openweatherapp.app.BaseActivity;
import com.example.galileo.openweatherapp.data.models.DataObject;
import com.example.galileo.openweatherapp.data.models.Weather;

import java.util.List;

import butterknife.BindView;

public class WeatherDetails extends BaseActivity {

    @BindView(R.id.tv_loc) TextView tv_loc;
    @BindView(R.id.tv_temp) TextView tv_temp;
    @BindView(R.id.tv_weather) TextView tv_weather;
    @BindView(R.id.tv_speed) TextView tv_speed;
    @BindView(R.id.tv_description) TextView tv_description;
    @BindView(R.id.tv_lonlat) TextView tv_lonlat;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_weather_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDetails();
    }

    public void initDetails() {
        DataObject dataObject = Application.getDataObject();

        String loc = dataObject.getName();
        String temp = dataObject.getMain().getTemp()+"";
        String weatherForecast = getString(R.string.label_na);
        String weatherDesc = getString(R.string.label_na);
        List<Weather> weather = dataObject.getWeather();
        if(weather != null) {
            if(weather.size() > 0){
                weatherForecast = weather.get(0).getMain();
                weatherDesc = weather.get(0).getDescription();
            }
        }

        double speed = dataObject.getWind().getSpeed();
        double lon = dataObject.getCoord().getLon();
        double lat = dataObject.getCoord().getLat();

        tv_loc.append(loc);
        tv_temp.append(temp);
        tv_weather.append(weatherForecast);
        tv_speed.append(speed+"");
        tv_description.append(""+weatherDesc);
        tv_lonlat.append("Latitude: "+lat+ " \nLongitude: "+lon);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
