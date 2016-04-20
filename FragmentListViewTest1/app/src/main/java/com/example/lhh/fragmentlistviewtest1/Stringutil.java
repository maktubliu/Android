package com.example.lhh.fragmentlistviewtest1;

/**
 * Created by LHH on 2016/4/20.
 */
public class Stringutil {
    public static boolean isEmpty(String str){
        if(str == null && str.trim().length() == 0){
            return true;
        }else {
            return false;
        }
    }
    public static String preUrl(String url){
        if(url == null){
            return null;
        }
        if(url.startsWith("http://")||url.startsWith("https://")){
            return url;
        }
        else{
            return url + "http://";
        }
    }
}