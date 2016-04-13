package com.example.lhh.coolweather.util;

import android.text.TextUtils;

import com.example.lhh.coolweather.db.CoolWeatherDB;
import com.example.lhh.coolweather.model.City;
import com.example.lhh.coolweather.model.County;
import com.example.lhh.coolweather.model.Province;

/**
 * Created by LHH on 2016/3/21.
 */
public class Utility {
    public synchronized static boolean handProvinceResponse(CoolWeatherDB coolWeatherDB, String response){
        if (!TextUtils.isEmpty(response)){
            String[] allProvince = response.split(",");
            if (allProvince != null && allProvince.length>0){
                for(String p:allProvince){
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceName(array[1]);
                    province.setProvinceCode(array[0]);
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }
    public synchronized static boolean handCityResponse(CoolWeatherDB coolWeatherDB, String response, int provinceId){
        if (!TextUtils.isEmpty(response)){
            String[] allCity = response.split(",");
            if (allCity != null && allCity.length>0){
                for(String p:allCity){
                    String[] array = p.split("\\|");
                    City city = new City();
                    city.setCityName(array[1]);
                    city.setCityCode(array[0]);
                    city.setProvinceId(provinceId);
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
    public synchronized static boolean handCountyResponse(CoolWeatherDB coolWeatherDB, String response, int cityId){
        if (!TextUtils.isEmpty(response)){
            String[] allCounty = response.split(",");
            if (allCounty != null && allCounty.length>0){
                for(String p:allCounty){
                    String[] array = p.split("\\|");
                    County county = new County();
                    county.setCountyName(array[1]);
                    county.setCountyCode(array[0]);
                    county.setCityId(cityId);
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }

}
