package com.example.lhh.network1;

/**
 * Created by LHH on 2016/3/22.
 */
public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
