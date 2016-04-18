package com.example.lhh.networktest;

import android.util.Xml;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import org.xmlpull.v1.XmlPullParser;

/**
 * Created by LHH on 2016/4/18.
 */
public class XmlRequest extends Request<XmlPullParser>{
    private final Response.Listener<XmlPullParser> mListener;
    public XmlRequest(int method, String url, Response.Listener<XmlPullParser> listener, Response.ErrorListener errorListener){
        super(method, url, errorListener);
        mListener = listener;
    }
    public XmlRequest(String url, Response.Listener<XmlPullParser> listener, Response.ErrorListener errorListener){
        this(Method.GET, url, listener, errorListener);
    }
    @Override
    protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response){

    }

}
