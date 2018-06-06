package com.example.galileo.openweatherapp.app;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.galileo.openweatherapp.data.models.DataObject;
import com.example.galileo.openweatherapp.utility.GLocBase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.FirebaseDatabase;
import com.osama.firecrasher.CrashListener;
import com.osama.firecrasher.FireCrasher;

import io.fabric.sdk.android.Fabric;

public class Application extends android.app.Application {

    public static final String TAG = Application.class.getSimpleName();
    private static Application mInstance;

    private static GLocBase gLocBase;
    private static DataObject dataObject;

    public static synchronized Application getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        Log.e("App","created");
        MultiDex.install(this);
        mInstance = this;
        super.onCreate();

        FireCrasher.install(this, new CrashListener() {
            @Override
            public void onCrash(Throwable throwable, final Activity activity) {
                recover(activity);
                FirebaseCrash.report(new Exception(throwable));
                Fabric.with(getApplicationContext(), new Crashlytics());
            }
        });

        gLocBase = GLocBase.getInstance(this);

    }

    public static DataObject getDataObject() {
        return dataObject;
    }

    public static void setDataObject(DataObject dataObject) {
        Application.dataObject = dataObject;
    }

    public static GLocBase getgLocBase() {
        return gLocBase;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        MultiDex.install(this);
    }

}

