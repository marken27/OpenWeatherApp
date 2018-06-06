package com.example.galileo.openweatherapp.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.galileo.openweatherapp.data.helper.DbHelper;
import com.example.galileo.openweatherapp.data.models.DataObject;
import com.example.galileo.openweatherapp.data.models.Information;
import com.example.galileo.openweatherapp.data.models.Main;
import com.example.galileo.openweatherapp.data.models.Weather;

import java.util.ArrayList;
import java.util.List;

public class MainDao extends WeatherDao{

    static MainDao membersDao = null;

    public synchronized static MainDao getInstance(Context context) {
        if(membersDao == null) {
            membersDao = new MainDao(context);
        } return (membersDao);
    }

    public MainDao(Context con) {
        super(con);
    }

    public void insertMain(Main model, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

            String temp = model.getTemp()+"";
            String preassure = model.getPressure()+"";

            String selectQuery = "SELECT * FROM " + getDbContants().TBL_MAIN + " WHERE " +
                    getDbContants().PK_ID + " = '" + id + "'";

            Cursor c = db.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(getDbContants().PK_ID, id);
            values.put(getDbContants().COL_TEMP, temp);
            values.put(getDbContants().COL_PRESSURE, preassure);
            if (c.getCount() > 0) {

                Log.e("-----main dao", "insert pk id "+id);

                db.update(getDbContants().TBL_MAIN, values,
                        getDbContants().PK_ID + " = ?",
                        new String[]{id});
            } else {
                long i = db.insert(getDbContants().TBL_MAIN, null, values);

                Log.e("-----main dao", "update pk id "+id);

        }
        c.close();
        if(!db.isOpen()) db.close();
    }

    public Main getMainById(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = null;

        try {

            String selectQuery = " SELECT * FROM " + getDbContants().TBL_MAIN  + " WHERE " +
                    getDbContants().PK_ID + " = '" + id + "'";

            c = db.rawQuery(selectQuery,null);

            Main model = new Main();

            if (c.getCount() > 0) {
                if (c.moveToFirst()) {

                    model.setTemp(Double.parseDouble(c.getString((c.getColumnIndex(getDbContants().COL_TEMP)))));
                    model.setPressure(Double.parseDouble(c.getString((c.getColumnIndex(getDbContants().COL_PRESSURE)))));

                    c.close();
                }
            }
            return model;

        } catch (Exception ex){
            Log.e("=-main", ex.toString());
            return new Main();
        }finally {
            db.close();
        }
    }
}
