package com.example.lhh.fragmentlistviewtest1;

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
public class CountyActivity extends Activity {
    private ListView mListView;
    private int[] countyids = {R.id.id_weather_countylist, R.id.id_weather_county_detail, R.id.id_weather_county_temp,R.id.id_weather_city_temp_now,R.id.id_weather_county_wind, R.id.id_weather_county_winddir,R.id.id_weather_county_windpower, R.id.id_weather_county_humidity, R.id.id_weather_city_updatetime};
    private static final String[] countykeys = {"county", "county_detail", "county_temp", "county_temp_now", "county_wind", "county_winddir", "county_windpower", "county_humidity", "county_updatetime"};
    private List<Map<String, String>> weatherDataList;
    private SimpleAdapter adapter;
    //Bundle extras = getIntent().getExtras();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fr_request);
        weatherDataList = new ArrayList<Map<String, String>>();
        mListView = (ListView) findViewById(R.id.lv_weather);
        adapter = new SimpleAdapter(this, weatherDataList,R.layout.fr_xml_request_county_list, countykeys, countyids);
        mListView.setAdapter(adapter);
        //String url = extras.getString("pyname");
        Intent intent = getIntent();
        String url = intent.getStringExtra("pynamel");
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
                                            weathermap.put("county", xmlPullParser.getAttributeValue(2));
                                            weathermap.put("county_detail", xmlPullParser.getAttributeValue(8));
                                            weathermap.put("county_temp", xmlPullParser.getAttributeValue(9) + "℃ 到 "+ xmlPullParser.getAttributeValue(10) + "℃");
                                            weathermap.put("county_temp_now", xmlPullParser.getAttributeValue(11)+"℃");
                                            weathermap.put("county_wind", xmlPullParser.getAttributeValue(12));
                                            weathermap.put("county_winddir",xmlPullParser.getAttributeValue(13));
                                            weathermap.put("county_windpower", xmlPullParser.getAttributeValue(14));
                                            weathermap.put("county_humidity", xmlPullParser.getAttributeValue(15));
                                            weathermap.put("county_updatetime", xmlPullParser.getAttributeValue(16));
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