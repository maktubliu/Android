package com.example.lhh.locationtest1;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.Provider;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private String provider;
    private BMapManager bMapManager;
    private MapView mapView;
    private final double lat = 31.9777;
    private final double lng = 118.767481;
    private Button go;
    private TextView addressview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.map_view);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);
        if(providerList.contains(LocationManager.GPS_PROVIDER)){
            provider = LocationManager.GPS_PROVIDER;
        }else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
            provider = LocationManager.NETWORK_PROVIDER;
        }else{
            Toast.makeText(this, "no network or gps", Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if(location != null){
            navigate(location);
        }
        //go = (Button) findViewById(R.id.parseBtn);
        //addressview = (TextView) findViewById(R.id.addressview);
    }
    private void navigate(Location location){
        
    }
    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }
    /*private static JSONObject geocodeAddr(double lat, double lng){
        String urlString = "http://api.map.baidu.com/geocoder/v2/?ak=wbp1pfY8XNWQhSngmQuxXCFR&callback=renderReverse&location=+" + lat + "," + lng +"&output=json&pois=1";
        StringBuilder sTotalString = new StringBuilder();
        try{
            URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlConnection;
            InputStream inputStream = httpsURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while((line = bufferedReader.readLine()) != null){
                sTotalString.append(line);
            }
            bufferedReader.close();
            httpsURLConnection.disconnect();
        }catch (Exception e){
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject = new JSONObject(sTotalString.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
    private static String getAddressByLaLng(double lat, double lng){
        String address = null;
        JSONObject jsonObject = geocodeAddr(lat, lng);
        try{
            JSONArray placemarks = jsonObject.getJSONArray("placemarks");
            JSONObject place = placemarks.getJSONObject(0);
            address = place.getString("address");
        }catch (Exception e){
            e.printStackTrace();
        }
        return address;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.parseBtn:
                String address = getAddressByLaLng(lat, lng);
                addressview.setText(address);
                break;
            default:
                break;
        }
    }*/

}
