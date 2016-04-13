package com.example.lhh.coolweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lhh.coolweather.R;
import com.example.lhh.coolweather.db.CoolWeatherDB;
import com.example.lhh.coolweather.model.City;
import com.example.lhh.coolweather.model.County;
import com.example.lhh.coolweather.model.Province;
import com.example.lhh.coolweather.util.HttpCallbackListener;
import com.example.lhh.coolweather.util.HttpUtil;
import com.example.lhh.coolweather.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LHH on 2016/3/21.
 */
public class ChooseAreaAcitivity extends Activity {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    private TextView titleView;
    private ListView listView;
    private ProgressDialog progressDialog;
    private ArrayAdapter<String> adapter;
    private List<String> datalist = new ArrayList<String>();
    private CoolWeatherDB coolWeatherDB;
    private List<Province> provinceList;
    private List<City> cityList;
    private List<County> countyList;
    private Province selectedprovince;
    private City selectedcity;
    private County selectedcounty;
    private int currentlevel;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listView = (ListView) findViewById(R.id.list_view);
        titleView = (TextView) findViewById(R.id.text_view);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datalist);//将adapter和datalist联系起来
        listView.setAdapter(adapter);//将adapter和listView链接起来
        coolWeatherDB = CoolWeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
                if (currentlevel == LEVEL_PROVINCE){
                    selectedprovince = provinceList.get(index);
                    querycity();
                }else if(currentlevel == LEVEL_CITY){
                    selectedcity = cityList.get(index);
                    querycounty();
                }
            }
        });
        queryprovince();
    }
    private void queryprovince(){
        provinceList = coolWeatherDB.loadProvinces();
        if (provinceList.size()>0){
            datalist.clear();
            for (Province province : provinceList){
                datalist.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleView.setText("中国");
            currentlevel = LEVEL_PROVINCE;
        }else{
            queryFromServer(null, "province");
        }
    }
    private void querycity(){
        cityList = coolWeatherDB.loadCity(selectedprovince.getId());
        if (cityList.size()>0){
            datalist.clear();
            for(City city : cityList){
                datalist.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleView.setText(selectedprovince.getProvinceName());
            currentlevel = LEVEL_CITY;
        }else{
            queryFromServer(selectedprovince.getProvinceCode(), "city");
        }
    }
    private void querycounty(){
        countyList = coolWeatherDB.loadCounty(selectedcity.getId());
        if(countyList.size() > 0){
            datalist.clear();
            for(County county : countyList){
                datalist.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleView.setText(selectedcity.getCityName());
            currentlevel = LEVEL_COUNTY;
        }else{
            queryFromServer(selectedcity.getCityCode(), "county");
        }
    }
    private void queryFromServer(final String code, final String type){
        String address;
        if(!TextUtils.isEmpty(code)){
            address="http://www.weather.com.cn/data/list3/city" + code + ".xml";
        }else{
            address="http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sentHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if ("province".equals(type)){
                    result = Utility.handProvinceResponse(coolWeatherDB, response);
                }else if("city".equals(type)){
                    result = Utility.handCityResponse(coolWeatherDB, response, selectedprovince.getId());
                }else if ("county".equals(type)){
                    result = Utility.handCountyResponse(coolWeatherDB, response, selectedcity.getId());
                }
                if (result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)){
                                queryprovince();
                            }
                            if("city".equals(type)){
                                querycity();
                            }
                            if("county".equals(type)){
                                querycounty();
                            }
                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(ChooseAreaAcitivity.this, "加载失败。。。", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
    private void showProgressDialog(){
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载....");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }
    private void closeProgressDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }
    @Override
    public void onBackPressed(){
        if(currentlevel == LEVEL_COUNTY){
            querycity();
        }else if(currentlevel == LEVEL_CITY){
            queryprovince();
        }else{
            finish();
        }
    }
}
