package com.example.lhh.fragmentlistviewtest1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    private ListView mListView;
    private int[] cityids = {R.id.id_weather_city, R.id.id_weather_county, R.id.id_weather_city_detail,R.id.id_weather_city_temp,R.id.id_weather_city_temp_now, R.id.id_weather_city_wind,R.id.id_weather_city_winddir, R.id.id_weather_city_windpower, R.id.id_weather_city_humidity, R.id.id_weather_city_updatetime};
    private static final String[] citykeys = {"city", "county", "city_detail", "city_temp", "city_temp_now", "city_wind", "city_winddir", "city_windpower", "city_humidity", "city_updatetime"};
    private List<Map<String, String>> weatherDataList;
    private SimpleAdapter adapter;
    //Bundle extras = getIntent().getExtras();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fr_request);
        weatherDataList = new ArrayList<Map<String, String>>();
        mListView = (ListView) findViewById(R.id.lv_weather);
        adapter = new SimpleAdapter(this, weatherDataList,R.layout.city_request, citykeys, cityids);
        mListView.setAdapter(adapter);
        //String url = extras.getString("pyname");
        Intent intent = getIntent();
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
                                            weathermap.put("county", xmlPullParser.getAttributeValue(3));
                                            weathermap.put("city_detail", xmlPullParser.getAttributeValue(8));
                                            weathermap.put("city_temp", xmlPullParser.getAttributeValue(10) + "℃ 到 "+ xmlPullParser.getAttributeValue(9) + "℃");
                                            weathermap.put("city_temp_now", xmlPullParser.getAttributeValue(11)+"℃");
                                            weathermap.put("city_wind", xmlPullParser.getAttributeValue(12));
                                            weathermap.put("city_winddir",xmlPullParser.getAttributeValue(13));
                                            weathermap.put("city_windpower", xmlPullParser.getAttributeValue(14));
                                            weathermap.put("city_humidity", xmlPullParser.getAttributeValue(15));
                                            weathermap.put("city_updatetime", xmlPullParser.getAttributeValue(16));
                                            weathermap.put("pyname", xmlPullParser.getAttributeValue(5));
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Map<String, String> map1 = (Map<String, String>)adapter.get(position);
                ListView listView = (ListView)parent;
                Map<String, String> map = (Map<String, String>)listView.getItemAtPosition(position);
                String url = map.get("pyname");
                Intent intent = new Intent(MyApplication.getContext(), CountyActivity.class);
                intent.putExtra("pynamel", url);
                startActivity(intent);
            }
        });
        request.setTag(this);

        VolleyUtil.getQueue(this).add(request);
    }

}
