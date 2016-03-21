package com.example.lhh.coolweather.model;

/**
 * Created by LHH on 2016/3/16.
 */
public class City {
    private int id;
    private String cityname;
    private String citycode;
    private int provinceId;
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getCityName(){
        return cityname;
    }
    public void setCityName(String cityname){
        this.cityname = cityname;
    }
    public String getCityCode(){
        return citycode;
    }
    public void setCityCode(String citycode){
        this.citycode = citycode;
    }
    public int getProvinceId(){
        return provinceId;
    }
    public void setProvinceId(int provinceId){
        this.provinceId = provinceId;
    }

}
