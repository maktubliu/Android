package com.example.lhh.fragmentlistview;

/**
 * Created by LHH on 2016/4/20.
 */
public class Constans {
    public static final String DEFAULT_XML_URL = "http://flash.weather.com.cn/wmaps/xml/china.xml";
    //public static final String DEFAULT_XML_URL = "http://flash.weather.com.cn/wmaps/xml/chengdu.xml";
    public static String PreString(String url) {
        return "http://flash.weather.com.cn/wmaps/xml/"+url+".xml";
    }
    public static class Extra {
        public static final String FRAGMENT_INDEX="com.example.lhh.networktest.FRAGMENT_INDEX";
    }
}