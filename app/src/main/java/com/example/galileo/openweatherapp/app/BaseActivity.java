package com.example.galileo.openweatherapp.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


import com.example.galileo.openweatherapp.module.callbacks.PermissionsCallback;

import butterknife.ButterKnife;

/**
 * Created by GALILEO_KENNETH on 11/7/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private PermissionsCallback permissionsCallback;
    public Context context;
    public final int PERMISSION_REQUEST_CODE = 123;

    public void setPermissionsCallback(PermissionsCallback permissionsCallback) {
        this.permissionsCallback = permissionsCallback;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(getLayoutResourceId());

        ButterKnife.bind(this);
        context = this;
        //BaseSingleton.getInstance().initInstance(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected abstract int getLayoutResourceId();

    protected boolean useBackButton() {
        return true;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View view = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText) {
            View w = getCurrentFocus();
            int scrCoords[] = new int[2];
            if (w != null) {
                w.getLocationOnScreen(scrCoords);
                float x = event.getRawX() + w.getLeft() - scrCoords[0];
                float y = event.getRawY() + w.getTop() - scrCoords[1];

                if (event.getAction() == MotionEvent.ACTION_UP
                        && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w
                        .getBottom())) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
                            .getWindowToken(), 0);
                }
            }
        } return ret;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && GrantResult(grantResults)) {
                    Log.e("---permission", "granted");
                    permissionsCallback.onPermissionCallback(true);
                } else if (Build.VERSION.SDK_INT >= 23 && !shouldShowRequestPermissionRationale(permissions[0])) {
                    Intent i = new Intent();
                    i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    i.addCategory(Intent.CATEGORY_DEFAULT);
                    i.setData(Uri.parse("package:" + context.getPackageName()));
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    context.startActivity(i);
                } else {
                    Log.e("---permission", "!granted");
                    permissionsCallback.onPermissionCallback(false);
                } return;
            }
        }
    }

    public boolean GrantResult(int[] grantResults) {
        for(int i = 0; i < grantResults.length; i++) {
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        } return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
