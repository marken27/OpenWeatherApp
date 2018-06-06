package com.example.galileo.openweatherapp.data;

import com.example.galileo.openweatherapp.data.models.Coordinates;
import com.example.galileo.openweatherapp.data.models.DataObject;
import com.example.galileo.openweatherapp.data.models.Information;
import com.example.galileo.openweatherapp.data.models.Main;
import com.example.galileo.openweatherapp.data.models.Weather;
import com.example.galileo.openweatherapp.data.models.Wind;

import java.util.List;

public class ResWeather {

    int cnt;
    List<DataObject> list;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<DataObject> getList() {
        return list;
    }

    public void setList(List<DataObject> list) {
        this.list = list;
    }
}
