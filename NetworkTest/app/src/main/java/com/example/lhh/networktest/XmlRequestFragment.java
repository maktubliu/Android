package com.example.lhh.networktest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
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

    }

}
