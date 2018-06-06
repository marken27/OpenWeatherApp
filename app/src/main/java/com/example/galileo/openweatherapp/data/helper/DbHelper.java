package com.example.galileo.openweatherapp.data.helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.galileo.openweatherapp.data.helper.DbTable.CREATE_TBL_COORD;
import static com.example.galileo.openweatherapp.data.helper.DbTable.CREATE_TBL_INFO;
import static com.example.galileo.openweatherapp.data.helper.DbTable.CREATE_TBL_MAIN;
import static com.example.galileo.openweatherapp.data.helper.DbTable.CREATE_TBL_WEATHER;
import static com.example.galileo.openweatherapp.data.helper.DbTable.CREATE_TBL_WIND;

public class DbHelper extends SQLiteOpenHelper {

    DbContants dbContants = DbContants.getInstance();

    public Context context;
    private static final String TAG = "WEATHER";
    public static final String DB_NAME = "weathers.db";
    public static final int DB_VERSION = 1;

    public DbHelper(Context con) {
        super(con, DB_NAME, null, DB_VERSION);
        context = con;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TBL_INFO);
            db.execSQL(CREATE_TBL_MAIN);
            db.execSQL(CREATE_TBL_WEATHER);
            db.execSQL(CREATE_TBL_WIND);
            db.execSQL(CREATE_TBL_COORD);
        } catch(Exception e) {
            Log.e("dbAdapter", e.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DbTable.UPDATE_TBL_INFO);
            db.execSQL(DbTable.UPDATE_TBL_MAIN);
            db.execSQL(DbTable.UPDATE_TBL_WEATHER);
            db.execSQL(DbTable.UPDATE_TBL_WIND);
            db.execSQL(DbTable.UPDATE_TBL_COORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onCreate(db);
    }

    public void closeDb(){
        this.close();
    }

    public DbContants getDbContants() {
        return dbContants;
    }

    public void setDbContants(DbContants dbContants) {
        this.dbContants = dbContants;
    }

}
