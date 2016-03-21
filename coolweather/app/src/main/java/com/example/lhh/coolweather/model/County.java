package com.example.lhh.coolweather.model;

/**
 * Created by LHH on 2016/3/16.
 */
public class County {
    private int id;
    private String countyname;
    private String countycode;
    private int cityId;
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getCountyName(){
        return countyname;
    }
    public void setCountyName(String countyname){
        this.countyname = countyname;
    }
    public String getCountyCode(){
        return countycode;
    }
    public void setCountyCode(String countycode){
        this.countycode = countycode;
    }
    public int getCityId(){
        return cityId;
    }
    public void setCityId(int cityId){
        this.cityId = cityId;
    }
}
