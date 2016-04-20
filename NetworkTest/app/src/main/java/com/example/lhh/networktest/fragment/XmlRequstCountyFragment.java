package com.example.lhh.networktest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.lhh.networktest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by LHH on 2016/4/19.
 */
public class XmlRequstCountyFragment extends android.support.v4.app.Fragment{
    public static final int INDEX = 33;
    private ListView lvcountyWeather;
    private static final int[] countyids = {R.id.id_weather_county, R.id.id_weather_county_detail,R.id.id_weather_county_temp, R.id.id_weather_county_temp_now, R.id.id_weather_county_wind, R.id.id_weather_county_winddir, R.id.id_weather_county_windpower, R.id.id_weather_county_humidity, R.id.id_weather_county_updatetime};
    private static final String[] countykeys = {"county", "county_detail", "county_temp", "county_temp_now", "county_wind", "county_winddir", "county_windpower", "county_humidity", "county_updatetime"};
    private List<Map<String, String>> weatherCountyDataList;
    private SimpleAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fr_xml_request_county_list, container, false);
        lvcountyWeather = (ListView) view.findViewById(R.id.lv_weather);
        weatherCountyDataList = new ArrayList<Map<String, String>>();
        adapter = new SimpleAdapter(getActivity(),weatherCountyDataList,R.layout.fr_xml_request_county_list, countykeys, countyids);

    }
}
