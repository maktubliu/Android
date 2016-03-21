package com.example.lhh.coolweather.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.lhh.coolweather.model.City;
import com.example.lhh.coolweather.model.County;
import com.example.lhh.coolweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LHH on 2016/3/18.
 */
public class CoolWeatherDB {
    public static final String DB_NAME = "cool_weather";
    public static final int VERSION = 1;
    private SQLiteDatabase db;
    private static CoolWeatherDB coolweatherDB;
    private CoolWeatherDB(Context context){
        CoolWeatherOpenHelper dbhelper = new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = dbhelper.getWritableDatabase();
    }
    public synchronized static CoolWeatherDB getInstance(Context context){
        if(coolweatherDB == null){
            coolweatherDB = new CoolWeatherDB(context);
        }
        return coolweatherDB;
    }
    public void saveProvince(Province province){
        if(province != null){
            ContentValues contentValues = new ContentValues();
            contentValues.put("province_name", province.getProvinceName());
            contentValues.put("province_code", province.getProvinceCode());
            db.insert("Province", null, contentValues);
        }
    }
    public List<Province> loadProvinces(){
        List<Province> list = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do {
                Province province = new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));

                list.add(province);
            }while (cursor.moveToNext());
        }
        return list;
    }
    public void saveCity(City city){
        if(city != null){
            ContentValues contentValues = new ContentValues();
            contentValues.put("city_name", city.getCityName());
            contentValues.put("city_code", city.getCityCode());
            contentValues.put("province_id", city.getProvinceId());
            db.insert("City", null, contentValues);
        }
    }
    public List<City> loadCity(){
        List<City> list = new ArrayList<>();
        Cursor cursor = db.query("City", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do {
                City city = new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
                list.add(city);
            }while(cursor.moveToNext());
        }
        return list;
    }
    public void saveCounty(County county){
        if(county != null){
            ContentValues contentValues = new ContentValues();
            contentValues.put("county_name", county.getCountyName());
            contentValues.put("county_code", county.getCountyCode());
            contentValues.put("city_id", county.getCityId());
            db.insert("County", null, contentValues);
        }
    }
    public List<County> loadCounty(){
        List<County> list = new ArrayList<>();
        Cursor cursor = db.query("County", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do {
                County county = new County();
                county.setId(cursor.getInt(cursor.getColumnIndex("id")));
                county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
                county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
                county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
                list.add(county);
            }while(cursor.moveToNext());
        }return list;
    }
}
