package com.example.galileo.openweatherapp.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.galileo.openweatherapp.data.models.DataObject;
import com.example.galileo.openweatherapp.data.models.Information;
import com.example.galileo.openweatherapp.data.models.Weather;

import java.util.ArrayList;
import java.util.List;

public class InformationDao extends MainDao {

    static InformationDao membersDao = null;

    public synchronized static InformationDao getInstance(Context context) {
        if(membersDao == null) {
            membersDao = new InformationDao(context);
        } return (membersDao);
    }

    public InformationDao(Context con) {
        super(con);
    }

    public void insertInformations(List<DataObject> models) {
        SQLiteDatabase db = this.getWritableDatabase();

        for(DataObject model : models) {
            String id = model.getId()+"";
            String type = model.getSys().getType()+"";
            String msg = model.getSys().getMessage()+"";
            String cntry = model.getSys().getCountry();
            String city = model.getName();

            String selectQuery = "SELECT * FROM " + getDbContants().TBL_INFO + " WHERE " +
                    getDbContants().PK_ID + " = '" + id + "'";

            Cursor c = db.rawQuery(selectQuery, null);

            ContentValues values = new ContentValues();

            values.put(getDbContants().PK_ID, id);
            values.put(getDbContants().COL_TYPE, type);
            values.put(getDbContants().COL_MESSAGE, msg);
            values.put(getDbContants().COL_CNTRY, cntry);
            values.put(getDbContants().COL_CITY, city);
            if (c.getCount() > 0) {

                Log.e("-----info dao", "insert pk id "+id);

                db.update(getDbContants().TBL_INFO, values,
                        getDbContants().PK_ID + " = ?",
                        new String[]{id});
            } else {
                long i = db.insert(getDbContants().TBL_INFO, null, values);

                Log.e("-----info dao", "update pk id "+id);

            }

            insertMain(model.getMain(), id);
            if(model.getWeather() != null && model.getWeather().size() > 0) {
                insertWeather(model.getWeather().get(0), id);
            }
            insertWind(model.getWind(), id);
            insertCoordinate(model.getCoord(), id);

            c.close();
        } if(!db.isOpen()) db.close();
    }

    public List<DataObject> getWeatherDataList() {
        List<DataObject> models = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = null;

        try {

            String selectQuery = " SELECT * FROM " + getDbContants().TBL_INFO;

            c = db.rawQuery(selectQuery,null);

            Log.e("=-=-=-infoDao", c.getCount() + " count");

            if (c.getCount() > 0) {
                if (c.moveToFirst()) {
                    do {
                        DataObject model = new DataObject();

                        Information sys = new Information();
                        int id = Integer.parseInt(c.getString((c.getColumnIndex(getDbContants().PK_ID))));
                        sys.setId(id);
                        sys.setType(Integer.parseInt(c.getString((c.getColumnIndex(getDbContants().COL_TYPE)))));
                        sys.setMessage(Double.parseDouble(c.getString((c.getColumnIndex(getDbContants().COL_MESSAGE)))));
                        sys.setCountry(c.getString((c.getColumnIndex(getDbContants().COL_CNTRY))));
                        model.setName(c.getString((c.getColumnIndex(getDbContants().COL_CITY))));

                        model.setSys(sys);

                        Log.e("-0-0-0", id+"");
                        List<Weather> list = new ArrayList<>();
                        list.add(getWeatherById(id+""));

                        model.setWeather(list);
                        model.setWind(getWindById(id+""));
                        model.setMain(getMainById(id+""));
                        model.setCoord(getCoordById(id+""));

                        models.add(model);
                    }while (c.moveToNext());
                }
                c.close();
            }
            return models;

        }catch (Exception ex){
            return models;
        }finally {
            db.close();
        }
    }

    /*public DataObject getWeatherDataById(String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = null;

        try {

            String selectQuery = " SELECT * FROM " + getDbContants().TBL_INFO + " info, " +
                    getDbContants().TBL_MAIN + " main, " +
                    getDbContants().TBL_WEATHER + " weather, " +
                    getDbContants().TBL_WIND + " wind, " +
                    getDbContants().TBL_COORD + " coord WHERE " +
                    "info." + PK_ID + " = id AND " +
                    "main." + PK_ID + " = id AND "+
                    "weather." + PK_ID + " = id AND "+
                    "wind." + PK_ID + " = id AND "+
                    "coord." + PK_ID + " = id ";

            c = db.rawQuery(selectQuery,null);
            DataObject model = new DataObject();

            Log.e("=-=-=-infoDao", c.getCount() + " count");

            if (c.getCount() > 0) {
                if (c.moveToFirst()) {

                    Coordinates coord = new Coordinates();
                    double lat = 0.0;
                    double lon = 0.0;

                    coord.setLat(Double.parseDouble(c.getString((c.getColumnIndex(getDbContants().COL_LAT)))));
                    coord.setLon(Double.parseDouble(c.getString((c.getColumnIndex(getDbContants().COL_LON)))));
                    model.setCoord(coord);

                    Information sys = new Information();
                    sys.setType(Integer.parseInt(c.getString((c.getColumnIndex(getDbContants().COL_TYPE)))));
                    sys.setMessage(Double.parseDouble(c.getString((c.getColumnIndex(getDbContants().COL_MESSAGE)))));
                    sys.setCountry(c.getString((c.getColumnIndex(getDbContants().COL_CNTRY))));
                    model.setSys(sys);

                    *//*List<Weather> weather = new ArrayList<>();
                    Main main = new Main();
                    Wind wind = new Wind();*//*

                    c.close();
                }
            }
            return model;

        }catch (Exception ex){
            return new DataObject();
        }
    }*/

}
