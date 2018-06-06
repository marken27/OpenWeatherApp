package com.example.galileo.openweatherapp.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.galileo.openweatherapp.data.helper.DbHelper;
import com.example.galileo.openweatherapp.data.models.Weather;
import com.example.galileo.openweatherapp.data.models.Wind;

public class WindDao extends CoordinateDao{

    static WindDao membersDao = null;

    public synchronized static WindDao getInstance(Context context) {
        if(membersDao == null) {
            membersDao = new WindDao(context);
        } return (membersDao);
    }

    public WindDao(Context con) {
        super(con);
    }

    public void insertWind(Wind model, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

            String speed = model.getSpeed()+"";
            String deg = model.getDeg()+"";

            String selectQuery = "SELECT * FROM " + getDbContants().TBL_WIND + " WHERE " +
                    getDbContants().PK_ID + " = '" + id + "'";

            Cursor c = db.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(getDbContants().PK_ID, id);
            values.put(getDbContants().COL_SPEED, speed);
            values.put(getDbContants().COL_DEG, deg);
            if (c.getCount() > 0) {

                Log.e("-----weather dao", "insert pk id "+id);

                db.update(getDbContants().TBL_WIND, values,
                        getDbContants().PK_ID + " = ?",
                        new String[]{id});
            } else {
                long i = db.insert(getDbContants().TBL_WIND, null, values);

                Log.e("-----weather dao", "update pk id "+id);

        }
        c.close();
        if(!db.isOpen()) db.close();
    }

    public Wind getWindById(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = null;

        try {

            String selectQuery = " SELECT * FROM " + getDbContants().TBL_WIND + " WHERE " +
                    getDbContants().PK_ID + " = '" + id + "'";

            c = db.rawQuery(selectQuery,null);

            Wind model = new Wind();

            if (c.getCount() > 0) {
                if (c.moveToFirst()) {

                    model.setSpeed(Double.parseDouble(c.getString((c.getColumnIndex(getDbContants().COL_SPEED)))));
                    model.setDeg(Double.parseDouble(c.getString((c.getColumnIndex(getDbContants().COL_DEG)))));

                    c.close();
                }
            }
            return model;

        } catch (Exception ex){
            return new Wind();
        }finally {
            db.close();
        }
    }

}
