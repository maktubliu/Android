package com.example.lhh.networktest.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.lhh.networktest.R;
import com.example.lhh.networktest.custom.XmlRequest;
import com.example.lhh.networktest.util.Constans;
import com.example.lhh.networktest.util.Stringutil;
import com.example.lhh.networktest.util.Toastutil;
import com.example.lhh.networktest.util.Volleyutil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LHH on 2016/4/18.
 */
public class XmlRequestFragment extends Fragment {
    public static final int INDEX = 31;
    private ListView lvweather;
    private static final int[] ids = {R.id.id_weather_city, R.id.id_weather_detail, R.id.id_weather_temp, R.id.id_weather_wind};
    private static final String[] keys = {"city", "detail", "temp", "wind"};
    private List<Map<String, String>> weatherDataList;
    private SimpleAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fr_xml_request, container, false);
        weatherDataList = new ArrayList<Map<String, String>>();
        lvweather = (ListView) view.findViewById(R.id.lv_weather);
        adapter = new SimpleAdapter(getActivity(),weatherDataList,R.layout.fr_xml_request_list,keys, ids);
        lvweather.setAdapter(adapter);
        XmlRequest request = new XmlRequest(Stringutil.preUrl(Constans.DEFAULT_XML_URL),
                new Response.Listener<XmlPullParser>() {
                    @Override
                    public void onResponse(XmlPullParser xmlPullParser) {
                        try {
                            weatherDataList.clear();
                            int eventType = xmlPullParser.getEventType();
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                switch (eventType) {
                                    case XmlPullParser.START_TAG:
                                        String nodeName = xmlPullParser.getName();
                                        if("city".equals(nodeName)){
                                            Map<String, String> weathermap = new HashMap<String, String>();
                                            weathermap.put("city", xmlPullParser.getAttributeValue(2));
                                            weathermap.put("detail", xmlPullParser.getAttributeValue(5));
                                            weathermap.put("temp", xmlPullParser.getAttributeValue(7) + "℃ 到 "+ xmlPullParser.getAttributeValue(6) + "℃");
                                            weathermap.put("wind", xmlPullParser.getAttributeValue(8));
                                            weatherDataList.add(weathermap);
                                        }
                                        break;
                                }
                                eventType = xmlPullParser.next();
                            }
                            adapter.notifyDataSetChanged();
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                Toastutil.ShowToast(getActivity(),getResources().getString(R.string.request_fail_text));
            }
        });
        request.setTag(this);
        Volleyutil.getQueue(getActivity()).add(request);
        return view;

    }
    @Override
    public void onDestroyView(){
        Volleyutil.getQueue(getActivity()).cancelAll(this);
        super.onDestroyView();
    }

}
