package com.example.lhh.network1;

import com.android.volley.toolbox.HurlStack;
import com.squareup.okhttp.OkUrlFactory;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;

/**
 * Created by LHH on 2016/3/23.
 */
public class OkHttpStack extends HurlStack{
    /*private com.squareup.okhttp.OkHttpClient okHttpClient;
    //private OkHttpClient okHttpClient;
    public OkHttpStack
    public OkHttpStack(){
        this(new com.squareup.okhttp.OkHttpClient());
    }
    public OkHttpStack(OkHttpClient okHttpClient){
        this.okHttpClient = okHttpClient;
    }
    @Override
    public HttpURLConnection createconnection(URL url) throws IOException{
        OkUrlFactory okUrlFactory = new OkUrlFactory(okHttpClient);
        return okUrlFactory.open(url);
    }*/
    private com.squareup.okhttp.OkHttpClient okHttpClient;
    //private final OkUrlFactory okUrlFactory;
    //final Request request = Request.Builder
    public OkHttpStack(){
        this(new com.squareup.okhttp.OkHttpClient);
    }
    public OkHttpStack(com.squareup.okhttp.OkHttpClient okHttpClient){
        this.okHttpClient = okHttpClient;
    }
    @Override
    protected HttpURLConnection createconnection(URL url) throws IOException{
        OkUrlFactory okUrlFactory = new OkUrlFactory(okHttpClient);
        return okUrlFactory.open(url);
    }

}
