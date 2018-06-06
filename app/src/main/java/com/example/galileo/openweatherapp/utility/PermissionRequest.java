package com.example.galileo.openweatherapp.utility;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by Marvin Kenneth Gonzales on 24/1/18.
 */

public class PermissionRequest extends Activity {

    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private static PermissionRequest ourInstance = null;

    public synchronized static PermissionRequest Bind(Activity activity) {
        if(ourInstance == null) {
            ourInstance = new PermissionRequest(activity);
        } return (ourInstance);
    }

    public PermissionRequest(Activity activity) {
        this.activity = activity;
    }

    public boolean permissionsGranted(int[] permissionCodes, int requestCode) {

        Log.e("----", "permission");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M  && permissionCodes != null){

            for(String permission : getPermissions(permissionCodes)) {
                if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(activity,
                            getPermissions(permissionCodes),
                            requestCode);

                    return false;
                }
            }
        } return true;
    }

    public String[] getPermissions(int[] permissionCode) {
        String[] permissions = new String[permissionCode.length];
        for(int i = 0; i < permissionCode.length; i ++) {
            permissions[i] = getStringPermission(permissionCode[i]);
        } return permissions;
    }

    public String getStringPermission(int code) {
        switch (code) {
            case 1 : return Manifest.permission.RECEIVE_SMS;
            case 2 : return Manifest.permission.ACCESS_FINE_LOCATION;
            case 3 : return Manifest.permission.ACCESS_COARSE_LOCATION;
            case 4 : return Manifest.permission.CAMERA;
            case 5 : return Manifest.permission.READ_CONTACTS;
            case 6 : return Manifest.permission.READ_EXTERNAL_STORAGE;
            case 7 : return Manifest.permission.WRITE_EXTERNAL_STORAGE;
            case 8 : return Manifest.permission.CALL_PHONE;
            case 9 : return Manifest.permission.WRITE_EXTERNAL_STORAGE;
            case 10 : return Manifest.permission.RECORD_AUDIO;
            case 11 : return Manifest.permission.READ_PHONE_STATE;
            default: return "";
        }
    }
}