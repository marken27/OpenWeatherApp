package com.example.galileo.openweatherapp.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.galileo.openweatherapp.data.helper.DbHelper;
import com.example.galileo.openweatherapp.data.models.Main;
import com.example.galileo.openweatherapp.data.models.Weather;
import com.example.galileo.openweatherapp.data.models.Wind;

public class WeatherDao extends WindDao{

    static WeatherDao membersDao = null;

    public synchronized static WeatherDao getInstance(Context context) {
        if(membersDao == null) {
            membersDao = new WeatherDao(context);
        } return (membersDao);
    }

    public WeatherDao(Context con) {
        super(con);
    }

    public void insertWeather(Weather model, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

            String main = model.getMain()+"";
            String desc = model.getDescription()+"";

            String selectQuery = "SELECT * FROM " + getDbContants().TBL_WEATHER + " WHERE " +
                    getDbContants().PK_ID + " = '" + id + "'";

            Cursor c = db.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(getDbContants().PK_ID, id);
            values.put(getDbContants().COL_MAIN, main);
            values.put(getDbContants().COL_DESCRIPTION, desc);
            if (c.getCount() > 0) {

                Log.e("-----weather dao", "insert pk id "+id);

                db.update(getDbContants().TBL_WEATHER, values,
                        getDbContants().PK_ID + " = ?",
                        new String[]{id});
            } else {
                long i = db.insert(getDbContants().TBL_WEATHER, null, values);

                Log.e("-----weather dao", "update pk id "+id);

        }
        c.close();
        if(!db.isOpen()) db.close();
    }

    public Weather getWeatherById(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = null;

        try {

            String selectQuery = " SELECT * FROM " + getDbContants().TBL_WEATHER + " WHERE " +
                    getDbContants().PK_ID + " = '" + id + "'";

            c = db.rawQuery(selectQuery,null);

            Weather model = new Weather();

            if (c.getCount() > 0) {
                if (c.moveToFirst()) {

                    model.setMain(c.getString((c.getColumnIndex(getDbContants().COL_MAIN))));
                    model.setDescription(c.getString((c.getColumnIndex(getDbContants().COL_DESCRIPTION))));

                    c.close();
                }
            }
            return model;

        } catch (Exception ex){
            return new Weather();
        }finally {
            db.close();
        }
    }

}
