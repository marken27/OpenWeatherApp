package com.example.galileo.openweatherapp.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.galileo.openweatherapp.data.helper.DbHelper;
import com.example.galileo.openweatherapp.data.models.Coordinates;
import com.example.galileo.openweatherapp.data.models.Wind;

public class CoordinateDao extends DbHelper{

    static CoordinateDao membersDao = null;

    public synchronized static CoordinateDao getInstance(Context context) {
        if(membersDao == null) {
            membersDao = new CoordinateDao(context);
        } return (membersDao);
    }

    public CoordinateDao(Context con) {
        super(con);
    }

    public void insertCoordinate(Coordinates model, String id) {
        SQLiteDatabase db = this.getWritableDatabase();

            String lat = model.getLat()+"";
            String lon = model.getLon()+"";

            String selectQuery = "SELECT * FROM " + getDbContants().TBL_COORD + " WHERE " +
                    getDbContants().PK_ID + " = '" + id + "'";

            Cursor c = db.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(getDbContants().PK_ID, id);
            values.put(getDbContants().COL_LAT, lat);
            values.put(getDbContants().COL_LON, lon);
            if (c.getCount() > 0) {

                Log.e("-----weather dao", "insert pk id "+id+" lat "+ lat);

                db.update(getDbContants().TBL_COORD, values,
                        getDbContants().PK_ID + " = ?",
                        new String[]{id});
            } else {
                long i = db.insert(getDbContants().TBL_COORD, null, values);

                Log.e("-----weather dao", "update pk id "+id+" lat "+ lat);

        }
        c.close();
        if(!db.isOpen()) db.close();
    }

    public Coordinates getCoordById(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = null;

        try {

            String selectQuery = " SELECT * FROM " + getDbContants().TBL_COORD + " WHERE " +
                    getDbContants().PK_ID + " = '" + id + "'";

            c = db.rawQuery(selectQuery,null);

            Coordinates model = new Coordinates();

            if (c.getCount() > 0) {
                if (c.moveToFirst()) {

                    model.setLat(Double.parseDouble(c.getString((c.getColumnIndex(getDbContants().COL_LAT)))));
                    model.setLon(Double.parseDouble(c.getString((c.getColumnIndex(getDbContants().COL_LON)))));

                    c.close();
                }
            }
            return model;

        } catch (Exception ex){
            Log.e("=-coordinate", ex.toString());
            return new Coordinates();
        } finally {
            db.close();
        }
    }

}
