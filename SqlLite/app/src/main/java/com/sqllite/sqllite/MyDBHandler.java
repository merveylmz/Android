package com.sqllite.sqllite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.introspect.WithMember;
import com.sqllite.sqllite.model.City;
import com.sqllite.sqllite.model.Clouds;
import com.sqllite.sqllite.model.Coord;
import com.sqllite.sqllite.model.Lang;
import com.sqllite.sqllite.model.Main;
import com.sqllite.sqllite.model.Weather;
import com.sqllite.sqllite.model.WeatherData;
import com.sqllite.sqllite.model.Wind;


public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "weather.db";

    /*public static final String TABLE_CITY = "CREATE TABLE TABLE_CITY (Id INTEGER, CityName VARCHAR(55),FindName VARCHAR(55), Country VARCHAR(55), Zoom INTEGER)";
    public static final String TABLE_CLOUDS = "CREATE TABLE TABLE_CLOUDS (Al INTEGER )";
    public static final String TABLE_COORD = "CREATE TABLE TABLE_COORD (Lon DOUBLE, Lat DOUBLE)";
    public static final String TABLE_MAIN = "CREATE TABLE TABLE_MAIN (Temp DOUBLE, Pressure INTEGER, TempMin DOUBLE, TempMax DOUBLE, Humidity INTEGER)";
    public static final String TABLE_WEATHER = "CREATE TABLE TABLE_WEATHER (Id INTEGER, Main VARCHAR(55), Description VARCHAR(55), Icon VARCHAR(55))";
    public static final String TABLE_WIND = "CREATE TABLE TABLE_WIND (Speed DOUBLE, Gust DOUBLE, Deg INTEGER)";*/


    String CREATE_TABLE_CITY = "CREATE TABLE " +
            City.TABLE_CITY + "("
            + City.COLUMN_ID + " INTEGER,"
            + City.COLUMN_NAME + " TEXT,"
            + City.COLUMN_FINDNAME + " TEXT,"
            + City.COLUMN_COUNTRY + " TEXT,"
            + City.COLUMN_ZOOM + " INTEGER" + ")";

    String CREATE_TABLE_CLOUDS = "CREATE TABLE " +
            Clouds.TABLE_CLOUDS + "("
            + Clouds.COLUMN_ALL + " INTEGER" + ")";

    String CREATE_TABLE_COORD = "CREATE TABLE " +
            Coord.TABLE_COORD + "("
            + Coord.COLUMN_LON + " DOUBLE,"
            + Coord.COLUMN_LAT + " DOUBLE" + ")";

    String CREATE_TABLE_LANG = "CREATE TABLE " +
            Lang.TABLE_LANG+ "("
            + Lang.COLUMN_BG + " TEXT,"
            + Lang.COLUMN_EL + " TEXT,"
            + Lang.COLUMN_EN + " TEXT,"
            + Lang.COLUMN_ES + " TEXT,"
            + Lang.COLUMN_JA + " TEXT,"
            + Lang.COLUMN_LINK + " TEXT,"
            + Lang.COLUMN_NO + " TEXT,"
            + Lang.COLUMN_RU + " TEXT,"
            + Lang.COLUMN_TR + " TEXT,"
            + Lang.COLUMN_ZH + " TEXT" + ")";


    String CREATE_TABLE_MAIN = "CREATE TABLE " +
            Main.TABLE_MAIN + "("
            + Main.COLUMN_TEMP + " DOUBLE,"
            + Main.COLUMN_PRESSURE + " INTEGER,"
            + Main.COLUMN_TEMPMIN + " DOUBLE,"
            + Main.COLUMN_TEMPMAX + " DOUBLE,"
            + Main.COLUMN_HUMIDITY + " INTEGER" + ")";

    String CREATE_TABLE_WEATHER = "CREATE TABLE " +
            Weather.TABLE_WEATHER + "("
            + Weather.COLUMN_CITY_ID + " INTEGER,"
            + Weather.COLUMN_ID + " INTEGER,"
            + Weather.COLUMN_MAIN + " TEXT,"
            + Weather.COLUMN_DESCRIPTION + " TEXT,"
            + Weather.COLUMN_ICON + " TEXT" + ")";

    String CREATE_TABLE_WIND = "CREATE TABLE " +
            Wind.TABLE_WIND + "("
            + Wind.COLUMN_SPEED + " DOUBLE,"
            + Wind.COLUMN_GUST + " DOUBLE,"
            + Wind.COLUMN_DEG + " INTEGER" + ")";


    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase ODB) {

        ODB.execSQL(CREATE_TABLE_CITY);
        ODB.execSQL(CREATE_TABLE_CLOUDS);
        ODB.execSQL(CREATE_TABLE_COORD);
        ODB.execSQL(CREATE_TABLE_LANG);
        ODB.execSQL(CREATE_TABLE_MAIN);
        ODB.execSQL(CREATE_TABLE_WEATHER);
        ODB.execSQL(CREATE_TABLE_WIND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE " + City.TABLE_CITY);
        db.execSQL("DROP TABLE " + Clouds.TABLE_CLOUDS);
        db.execSQL("DROP TABLE " + Coord.TABLE_COORD);
        db.execSQL("DROP TABLE " + Lang.TABLE_LANG);
        db.execSQL("DROP TABLE " + Main.TABLE_MAIN);
        db.execSQL("DROP TABLE " + Weather.TABLE_WEATHER);
        db.execSQL("DROP TABLE " + Wind.TABLE_WIND);
        onCreate(db);

    }

    /*----------------- VERİ EKLEME ------------------*/

    public void addCity(WeatherData weatherData) {

        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues valuesCity = new ContentValues();
            ContentValues valuesClouds = new ContentValues();
            ContentValues valuesCoord = new ContentValues();
            ContentValues valuesLang = new ContentValues();
            ContentValues valuesMain = new ContentValues();
            ContentValues valuesWeather = new ContentValues();
            ContentValues valuesWind = new ContentValues();

            valuesCity.put(City.COLUMN_ID, weatherData.getCity().getId());
            valuesCity.put(City.COLUMN_NAME, weatherData.getCity().getName());
            valuesCity.put(City.COLUMN_FINDNAME, weatherData.getCity().getFindname());
            valuesCity.put(City.COLUMN_COUNTRY, weatherData.getCity().getCountry());
            valuesCity.put(City.COLUMN_ZOOM, weatherData.getCity().getZoom());

            valuesClouds.put(Clouds.COLUMN_ALL, weatherData.getClouds().getAll());

            valuesCoord.put(Coord.COLUMN_LON, weatherData.getCity().getCoord().getLon());
            valuesCoord.put(Coord.COLUMN_LAT, weatherData.getCity().getCoord().getLat());

            try{
                if(weatherData.getCity().getLangs().size() != 0){
                    valuesLang.put(Lang.COLUMN_LANG_ID, weatherData.getCity().getId());
                    valuesLang.put(Lang.COLUMN_BG, weatherData.getCity().getLangs().get(0).getBg());
                    valuesLang.put(Lang.COLUMN_EL, weatherData.getCity().getLangs().get(1).getEl());
                    valuesLang.put(Lang.COLUMN_EN, weatherData.getCity().getLangs().get(2).getEn());
                    valuesLang.put(Lang.COLUMN_ES, weatherData.getCity().getLangs().get(3).getEs());
                    valuesLang.put(Lang.COLUMN_JA, weatherData.getCity().getLangs().get(4).getJa());
                    valuesLang.put(Lang.COLUMN_LINK, weatherData.getCity().getLangs().get(5).getLink());
                    valuesLang.put(Lang.COLUMN_NO, weatherData.getCity().getLangs().get(6).getNo());
                    valuesLang.put(Lang.COLUMN_RU, weatherData.getCity().getLangs().get(7).getRu());
                    valuesLang.put(Lang.COLUMN_TR, weatherData.getCity().getLangs().get(8).getTr());
                    valuesLang.put(Lang.COLUMN_ZH, weatherData.getCity().getLangs().get(9).getZh());
                    db.insert(Lang.TABLE_LANG, null, valuesLang);
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            valuesMain.put(Main.COLUMN_TEMP, weatherData.getMain().getTemp());
            valuesMain.put(Main.COLUMN_PRESSURE, weatherData.getMain().getPressure());
            valuesMain.put(Main.COLUMN_TEMPMIN, weatherData.getMain().getTempMin());
            valuesMain.put(Main.COLUMN_TEMPMAX, weatherData.getMain().getTempMax());
            valuesMain.put(Main.COLUMN_HUMIDITY, weatherData.getMain().getHumidity());

            for (int i=0; i<weatherData.getWeather().size() ;i++){

                valuesWeather.put(Weather.COLUMN_CITY_ID, weatherData.getCity().getId());
                valuesWeather.put(Weather.COLUMN_ID, weatherData.getWeather().get(i).getId());
                valuesWeather.put(Weather.COLUMN_MAIN, weatherData.getWeather().get(i).getMain());
                valuesWeather.put(Weather.COLUMN_DESCRIPTION, weatherData.getWeather().get(i).getDescription());
                valuesWeather.put(Weather.COLUMN_ICON, weatherData.getWeather().get(i).getIcon());
                db.insert(Weather.TABLE_WEATHER, null, valuesWeather);
            }

            valuesWind.put(Wind.COLUMN_SPEED, weatherData.getWind().getSpeed());
            valuesWind.put(Wind.COLUMN_GUST, weatherData.getWind().getGust());
            valuesWind.put(Wind.COLUMN_DEG, weatherData.getWind().getDeg());

            db.insert(City.TABLE_CITY, null, valuesCity);
            db.insert(Clouds.TABLE_CLOUDS, null, valuesClouds);
            db.insert(Coord.TABLE_COORD, null, valuesCoord);
            db.insert(Main.TABLE_MAIN, null, valuesMain);
            db.insert(Wind.TABLE_WIND, null, valuesWind);

            db.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*----------------------------------------- SORGU ---------------------------------------------*/

    public City findCity(String name) {

        City city = new City();

        try{

            String query = "select id, name, findname, country, zoom from city where name='"+name+"'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                city.setId(Integer.parseInt(cursor.getString(0)));
                city.setName(cursor.getString(1));
                city.setFindname(cursor.getString(2));
                city.setCountry(cursor.getString(3));
                city.setZoom(Integer.parseInt(cursor.getString(4)));
                cursor.close();
            } else {
                city = null;
            }
            db.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return city;
    }


}