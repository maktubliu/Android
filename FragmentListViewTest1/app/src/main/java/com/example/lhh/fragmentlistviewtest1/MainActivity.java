package com.example.lhh.fragmentlistviewtest1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity{
    private ListView mListView;
    private int[] ids = {R.id.id_weather_province, R.id.id_weather_city, R.id.id_weather_detail, R.id.id_weather_temp, R.id.id_weather_wind};
    private String[] key = {"province", "city", "detail", "temp", "wind"};
    private List<Map<String, String>> weatherDataList;
    private SimpleAdapter adapter;
    //private String urlname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fr_request);
        weatherDataList = new ArrayList<Map<String, String>>();
        mListView = (ListView) findViewById(R.id.lv_weather);
        adapter = new SimpleAdapter(this, weatherDataList,R.layout.content_main, key, ids);
        mListView.setAdapter(adapter);
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
                                            weathermap.put("province", xmlPullParser.getAttributeValue(0));
                                            weathermap.put("city", xmlPullParser.getAttributeValue(2));
                                            weathermap.put("detail", xmlPullParser.getAttributeValue(5));
                                            weathermap.put("temp", xmlPullParser.getAttributeValue(7) + "℃ 到 "+ xmlPullParser.getAttributeValue(6) + "℃");
                                            weathermap.put("wind", xmlPullParser.getAttributeValue(8));
                                            weathermap.put("pyname", xmlPullParser.getAttributeValue(1));
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
                Intent intent = new Intent(MyApplication.getContext(), CityActivity.class);
                intent.putExtra("pyname", url);
                startActivity(intent);
            }
        });
        request.setTag(this);
        VolleyUtil.getQueue(this).add(request);
        //setContentView(mListView);
    }


}
