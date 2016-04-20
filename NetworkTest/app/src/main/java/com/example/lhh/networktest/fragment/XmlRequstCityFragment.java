package com.example.lhh.networktest.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.lhh.networktest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by LHH on 2016/4/19.
 */
public class XmlRequstCityFragment extends Fragment {
    public static final int INDEX = 32;
    private ListView lvcityWeather;
    private static final int[] cityids = {R.id.id_weather_city, R.id.id_weather_county, R.id.id_weather_city_detail,R.id.id_weather_city_temp,R.id.id_weather_city_temp_now, R.id.id_weather_city_wind, R.id.id_weather_city_winddir, R.id.id_weather_city_windpower,R.id.id_weather_city_humidity,R.id.id_weather_city_updatetime};
    private static final String[] citykeys = {"city", "county", "city_detail", "city_temp", "city_temp_now", "city_wind", "city_winddir", "city_windpower", "city_humidity", "city_updatetime"};
    private List<Map<String, String>> weatherCityDataList;
    private SimpleAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fr_xml_request_city_list, container, false);
        weatherCityDataList = new ArrayList<Map<String, String>>();
        lvcityWeather = (ListView) view.findViewById(R.id.lv_weather);
        adapter = new SimpleAdapter(getActivity(), weatherCityDataList, R.layout.fr_xml_request_city_list, citykeys, cityids);
        lvcityWeather.setAdapter(adapter);

    }
}
