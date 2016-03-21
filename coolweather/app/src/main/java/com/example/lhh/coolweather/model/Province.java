package com.example.lhh.coolweather.model;

/**
 * Created by LHH on 2016/3/15.
 */
public class Province {
    private int id;
    private String provincename;
    private String provincecode;
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getProvinceName(){
        return provincename;
    }
    public void setProvinceName(String name){
        this.provincename = name;
    }
    public String getProvinceCode(){
        return provincecode;
    }
    public void setProvinceCode(String code){
        this.provincecode = code;
    }

}
