package com.example.lhh.coolweather.util;

/**
 * Created by LHH on 2016/3/21.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
