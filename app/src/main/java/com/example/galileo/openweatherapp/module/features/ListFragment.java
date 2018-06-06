package com.example.galileo.openweatherapp.module.features;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.galileo.openweatherapp.R;
import com.example.galileo.openweatherapp.app.Application;
import com.example.galileo.openweatherapp.data.ResWeather;
import com.example.galileo.openweatherapp.data.dao.InformationDao;
import com.example.galileo.openweatherapp.data.models.DataObject;
import com.example.galileo.openweatherapp.data.models.Information;
import com.example.galileo.openweatherapp.module.adapter.CitiesAdapter;
import com.example.galileo.openweatherapp.module.object.GetWeatherForecastObject;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListFragment extends Fragment{

    @BindView(R.id.iv_btn_refresh) ImageView iv_btn_refresh;
    @BindView(R.id.rv_cities)RecyclerView rv_cities;

    CitiesAdapter adapter;
    LinearLayoutManager layoutManager;
    GetWeatherForecastObject getWeatherForecastObject;

    InformationDao informationDao;

    static ListFragment listFragment = null;

    ProgressDialog progressDialog;

    public synchronized static ListFragment getInstance() {
        if(listFragment == null) {
            listFragment = new ListFragment();
        } return listFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        informationDao = InformationDao.getInstance(getActivity().getApplicationContext());

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        getWeatherForecastObject = GetWeatherForecastObject.getInstance(getActivity().getApplicationContext());

        getWeatherForecastObject.setOnSuccess(new GetWeatherForecastObject.OnMessageSuccess() {
            @Override
            public void onMessageSuccess(boolean isSuccess, ResWeather response) {
                dismissDialog();

                if(isSuccess) {
                    List<DataObject> list = response.getList();
                    if(list != null && list.size() > 0) {
                        adapter.setWeatherList(response.getList());
                    } else makeText();
                } else makeText();

                updateUI();
            }
        });
    }

    public void makeText(){
        Toast.makeText(getActivity().getApplicationContext(),
                "Unable to fetch weather info",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View list = inflater.inflate(R.layout.fragment_list_of_cities, null);
        ButterKnife.bind(this, list);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.label_please_wait));

        init();

        return list;
    }

    public void init() {
        adapter = new CitiesAdapter();

        List<DataObject> list = informationDao.getWeatherDataList();

        if(list != null && list.size() > 0) adapter.setWeatherList(list);

        rv_cities.setHasFixedSize(true);
        rv_cities.setLayoutManager(layoutManager);
        rv_cities.setNestedScrollingEnabled(false);
        rv_cities.setAdapter(adapter);

        adapter.setOnItemClickListener(new CitiesAdapter.setOnClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                DataObject data = adapter.getWeatherList().get(position);
                Application.setDataObject(data);
                startActivity(new Intent(getActivity().getApplicationContext(), WeatherDetails.class));
            }
        });

        iv_btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
                getWeatherForecastObject.getWeatherForecast();
            }
        });

    }

    public void showDialog(){
        if(progressDialog != null &&
                !progressDialog.isShowing()) progressDialog.show();
    }

    public void dismissDialog(){
        if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
    }

    public void updateUI(){
        adapter.notifyDataSetChanged();
        rv_cities.invalidate();
    }
}
