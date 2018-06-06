package com.example.galileo.openweatherapp.module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.galileo.openweatherapp.R;
import com.example.galileo.openweatherapp.data.ResWeather;
import com.example.galileo.openweatherapp.data.models.DataObject;
import com.example.galileo.openweatherapp.data.models.Weather;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Marvin Kenneth Gonzales on 24/1/18.
 */

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.ViewHolder> {
    private Context mContext;
    private CitiesAdapter.setOnClickListener setOnClickListener;
    private List<DataObject> weatherList = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_loc) TextView tv_loc;
        @BindView(R.id.tv_temp) TextView tv_temp;
        @BindView(R.id.tv_weather) TextView tv_weather;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            setOnClickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }

        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.row_city, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        String loc = weatherList.get(position).getName();
        String temp = weatherList.get(position).getMain().getTemp() + "";
        String weatherForecast = "N/A";
        List<Weather> weather = weatherList.get(position).getWeather();
        if(weather != null) {
            if(weather.size() > 0){
                weatherForecast = weather.get(0).getMain();
            }
        }

        holder.tv_loc.setText(mContext.getString(R.string.label_weather) + loc);
        holder.tv_temp.setText(mContext.getString(R.string.label_temp)+temp);
        holder.tv_weather.setText(mContext.getString(R.string.label_loc)+weatherForecast);
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public void setOnItemClickListener(CitiesAdapter.setOnClickListener clickListener) {
        this.setOnClickListener = clickListener;
    }

    public interface setOnClickListener {
        public void onItemClick(int position, View v);
    }

    public CitiesAdapter() { }

    public List<DataObject> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<DataObject> weatherList) {
        this.weatherList = weatherList;
    }
}
