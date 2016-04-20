package com.example.lhh.fragmentlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LHH on 2016/4/20.
 */
public class CityActivity extends Activity {
    ListView mListView;
    private int[] cityids = {R.id.id_weather_city, R.id.id_weather_county, R.id.id_weather_city_detail,R.id.id_weather_city_temp,R.id.id_weather_city_temp_now, R.id.id_weather_city_wind,R.id.id_weather_city_winddir, R.id.id_weather_city_windpower, R.id.id_weather_city_humidity, R.id.id_weather_city_updatetime};
    private static final String[] citykeys = {"city", "county", "city_detail", "city_temp", "city_temp_now", "city_wind", "city_winddir", "city_windpower", "city_humidity", "city_updatetime"};
    private List<Map<String, String>> weatherDataList;
    private SimpleAdapter adapter;
    private String urlname;
    Intent intent = getIntent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherDataList = new ArrayList<Map<String, String>>();
        mListView = (ListView) findViewById(R.id.lv_weather);
        adapter = new SimpleAdapter(this, weatherDataList,R.layout.content_main, citykeys, cityids);
        mListView.setAdapter(adapter);
        String url = intent.getStringExtra("pyname");
        XmlRequest request = new XmlRequest(Stringutil.preUrl(Constans.PreString(url)),
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
                                            weathermap.put("detail", xmlPullParser.getAttributeValue(8));
                                            weathermap.put("temp", xmlPullParser.getAttributeValue(10) + "℃ 到 "+ xmlPullParser.getAttributeValue(9) + "℃");
                                            weathermap.put("temp_now", xmlPullParser.getAttributeValue(11)+"℃");
                                            weathermap.put("wind", xmlPullParser.getAttributeValue(12));
                                            weathermap.put("wind_dir",xmlPullParser.getAttributeValue(13));
                                            weathermap.put("wind_power", xmlPullParser.getAttributeValue(14));
                                            weathermap.put("humidity", xmlPullParser.getAttributeValue(15));
                                            weathermap.put("updatetime", xmlPullParser.getAttributeValue(16));
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
                Toastutil.ShowToast(MyApplication.getContext() ,getResources().getString(R.string.request_fail_text));
            }
        });
        request.setTag(this);

        VolleyUtil.getQueue(this).add(request);
    }

}
