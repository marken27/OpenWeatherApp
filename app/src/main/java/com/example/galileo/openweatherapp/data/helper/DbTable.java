package com.example.galileo.openweatherapp.data.helper;


public class DbTable extends DbContants {

    //static final String

    static final String CREATE_TBL_INFO = "CREATE TABLE " +
            TBL_INFO + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PK_ID + " TEXT, " +
            COL_TYPE + " TEXT, " +
            COL_MESSAGE + " TEXT, " +
            COL_CNTRY + " TEXT, " +
            COL_CITY + " TEXT);";

    static final String CREATE_TBL_MAIN = "CREATE TABLE " +
            TBL_MAIN + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PK_ID + " TEXT, " +
            COL_TEMP + " TEXT, " +
            COL_PRESSURE + " TEXT);";

    static final String CREATE_TBL_WEATHER = "CREATE TABLE " +
            TBL_WEATHER + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PK_ID + " TEXT, " +
            COL_MAIN + " TEXT, " +
            COL_DESCRIPTION + " TEXT);";


    static final String CREATE_TBL_WIND = "CREATE TABLE " +
            TBL_WIND + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PK_ID + " TEXT, " +
            COL_SPEED + " TEXT, " +
            COL_DEG + " TEXT);";

    static final String CREATE_TBL_COORD = "CREATE TABLE " +
            TBL_COORD + "(" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            PK_ID + " TEXT, " +
            COL_LON + " TEXT, " +
            COL_LAT + " TEXT);";


    static final String UPDATE_TBL_INFO = "DROP TABLE IF EXISTS " + TBL_INFO;
    static final String UPDATE_TBL_MAIN = "DROP TABLE IF EXISTS " + TBL_MAIN;
    static final String UPDATE_TBL_WEATHER = "DROP TABLE IF EXISTS " + TBL_WEATHER;
    static final String UPDATE_TBL_WIND = "DROP TABLE IF EXISTS " + TBL_WIND;
    static final String UPDATE_TBL_COORD = "DROP TABLE IF EXISTS " + TBL_COORD;
}
