package com.example.galileo.openweatherapp.module.features;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.galileo.openweatherapp.BuildConfig;
import com.example.galileo.openweatherapp.R;
import com.example.galileo.openweatherapp.app.BaseActivity;
import com.example.galileo.openweatherapp.data.ResWeather;
import com.example.galileo.openweatherapp.data.dao.InformationDao;
import com.example.galileo.openweatherapp.data.models.Information;
import com.example.galileo.openweatherapp.module.callbacks.PermissionsCallback;
import com.example.galileo.openweatherapp.module.object.GetWeatherForecastObject;
import com.example.galileo.openweatherapp.utility.PermissionRequest;
import com.example.galileo.openweatherapp.utility.Utility;

import java.util.List;

import butterknife.BindView;

import static com.example.galileo.openweatherapp.utility.Constants.LOCATION_COARSE_PERMISSION_CODE;
import static com.example.galileo.openweatherapp.utility.Constants.LOCATION_PERMISSION_CODE;
import static com.example.galileo.openweatherapp.utility.Constants.STORAGE_READ_PERMISSION_CODE;

public class SplashScreenActivity extends BaseActivity implements PermissionsCallback {

    private final String TAG = SplashScreenActivity.class.getSimpleName();

    private final String FLAVOR_DEV = "1";

    String BUILD_FLAVOR;

    @BindView(R.id.iv_logo) ImageView iv_logo;

    PermissionRequest permissionRequest;
    GetWeatherForecastObject getWeatherForecastObject;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set permission callback
        setPermissionsCallback(this);

        // initialize Objects
        permissionRequest = new PermissionRequest(this);
        getWeatherForecastObject = GetWeatherForecastObject.getInstance(this);

        // check is there's a network available
        if(Utility.isNetworkAvailable(this)) {
            // get weather forcast
            getWeatherForecastObject.getWeatherForecast();
        }

        // getting product flavor
        BUILD_FLAVOR = BuildConfig.BUILD_FLAVOR;

        // setImage by product flavor
        iv_logo.setImageResource(getSplashScreenByBuildFlavor());

        // check permissions
        if(checkPermission()) gotoMainPage();

    }

    public void gotoMainPage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), LandingPageActivity.class));
                finish();
            }
        }, 2000);
    }

    // get splash logo by product flavor
    public int getSplashScreenByBuildFlavor() {
        return BUILD_FLAVOR.equals(FLAVOR_DEV) ? R.drawable.img_dev_logo : R.drawable.img_logo;
    }

    public boolean checkPermission() {
        if (permissionRequest.permissionsGranted(new int[] {
                        LOCATION_COARSE_PERMISSION_CODE,
                        LOCATION_PERMISSION_CODE,
                        STORAGE_READ_PERMISSION_CODE},
                PERMISSION_REQUEST_CODE)) {
            return true;
        }return false;
    }

    @Override
    public void onPermissionCallback(boolean isPermissionGranted) {
        if(isPermissionGranted) gotoMainPage();
    }

}
