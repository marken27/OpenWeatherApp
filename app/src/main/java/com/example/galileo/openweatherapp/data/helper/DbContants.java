package com.example.galileo.openweatherapp.data.helper;

public class DbContants {

    static DbContants dbContants = null;

    public synchronized static DbContants getInstance() {
        if(dbContants == null) {
            dbContants = new DbContants();
        } return (dbContants);
    }

    // GLOBAL ID
    public static final String ID = "id";
    public static final String PK_ID = "pk_id";

    //  ==================== TABLE INFORMATION ===================== \\
    public static final String TBL_INFO = "tbl_info";
    // ======================> ID;
    // ======================> PK_ID;
    public static final String COL_TYPE = "type";
    public static final String COL_MESSAGE = "message";
    public static final String COL_CNTRY = "country";
    public static final String COL_CITY = "city";


    //  ==================== TABLE MAIN ===================== \\
    public static final String TBL_MAIN = "tbl_main";
    // ======================> ID;
    // ======================> PK_ID;
    public static final String COL_TEMP = "temp";
    public static final String COL_PRESSURE = "pressure";

    //  ==================== TABLE WEATHER ===================== \\
    public static final String TBL_WEATHER = "tbl_weather";
    // ======================> ID;
    // ======================> PK_ID;
    public static final String COL_MAIN = "main";
    public static final String COL_DESCRIPTION = "description";


    //  ==================== TABLE WIND ===================== \\
    public static final String TBL_WIND = "tbl_wind";
    // ======================> ID;
    // ======================> PK_ID;
    public static final String COL_SPEED = "speed";
    public static final String COL_DEG = "degree";

    //  ==================== TABLE COORDINATE ===================== \\
    public static final String TBL_COORD = "tbl_coordinate";
    // ======================> ID;
    // ======================> PK_ID;
    public static final String COL_LON = "lon";
    public static final String COL_LAT = "lat";
}
