package com.example.lhh.locationtest;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private TextView positionTextView;
    private LocationManager locationManager;
    private String provider;
    private static final int SHOW_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        positionTextView = (TextView) findViewById(R.id.position_text_view);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providerList = locationManager.getProviders(true);
        if(providerList.contains(LocationManager.GPS_PROVIDER)){
            provider = LocationManager.GPS_PROVIDER;
        }else if (providerList.contains(LocationManager.NETWORK_PROVIDER)){
            provider = LocationManager.NETWORK_PROVIDER;
        }else{
            Toast.makeText(this, "There is no location service open", Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if(location != null){
            showLocation(location);
        }
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(locationManager != null){
            locationManager.removeUpdates(locationListener);
        }
    }
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    private void showLocation(final Location location){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //String urlString = "http://ditu.google.com/maps/geo?q=+" + location.getLatitude() + "," + location.getLongitude() + "&output = json&oe = utf8&hl=zh-CN&sensor=false" ;
                String urlString = "http://api.map.baidu.com/geocoder/v2/?ak=wbp1pfY8XNWQhSngmQuxXCFR&callback=renderReverse&location=+" + location.getLatitude()+ "," + location.getLongitude() +"&output=json&pois=1";
                //"http://api.map.baidu.com/geocoder/v2/?ak=key&callback=renderReverse&location=+32.0699833,118.8147333&output=json&pois=0
                HttpsURLConnection connection = null;
                StringBuilder totalString = new StringBuilder();
                try{
                    URL url = new URL(urlString);
                    //URL url = new URL("" + location.getLatitude() + "," + location.getLongitude());
                    connection = (HttpsURLConnection)url.openConnection();
                    InputStream urlStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlStream));
                    //connection.setRequestMethod("GET");
                    //connection.setConnectTimeout(8000);
                    //connection.setReadTimeout(8000);
                    String line = "";
                    while((line = bufferedReader.readLine()) != null){
                        totalString.append(line);
                    }
                    bufferedReader.close();
                    connection.disconnect();
                }catch (Exception e){
                    e.printStackTrace();
                }
                JSONObject jsonObject = new JSONObject();
                try{
                   jsonObject = new JSONObject(totalString.toString());
                }catch (JSONException e){
                    e.printStackTrace();
                }

                /*StringBuilder url = new StringBuilder();
                url.append("http://api.map.baidu.com/geocoder/v2/?ak=E4805d16520de693a3fe707cdc962045&callback=renderReverse&location=");
                url.append(location.getLatitude()).append(",");
                url.append(location.getLongitude());
                URLConnection urlConnection = (URL)url
                HttpsURLConnection httpsURLConnection =*/
                try{
                    JSONArray placemarks = jsonObject.getJSONArray("results");
                    if(placemarks.length()>0){
                        JSONObject subObject = placemarks.getJSONObject(0);
                        String address = subObject.getString("formatted_address");
                        Message message = new Message();
                        message.what = SHOW_LOCATION;
                        message.obj = address;
                        handler.sendMessage(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }).start();
        //String currentPostion = "纬度：" + location.getLatitude() + "经度为：" + location.getLongitude();
        //positionTextView.setText(currentPostion);
    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_LOCATION:
                    String currentlocation = (String)msg.obj;
                    positionTextView.setText(currentlocation);
                    break;
                default:
                    break;
            }
        }
    };



}
