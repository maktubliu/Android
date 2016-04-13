package com.example.lhh.network1;

import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String DEBUG_TAG="network";
    private Button send_request;
    private TextView textView;
    private EditText editText;
    private static final String url = "www.baidu.com";
    private RequestQueue requestQueue = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send_request = (Button) findViewById(R.id.send_request);
        textView = (TextView) findViewById(R.id.response);
        send_request.setOnClickListener(this);
        editText = (EditText) findViewById(R.id.edit_text);
        requestQueue = Volley.newRequestQueue(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //httpget();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.send_request:
                //volleyget();
                break;
            default:
                break;
                //sendHttprequest();
        }
    }
    private void httpget(){
        OkHttpClient mokHttpClient = new OkHttpClient();
        final okhttp3.Request request = new Request.

    }
    /*private void volleyget(){
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", response);
                //response.setText(s.substring(0, 500));
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("TAG", error.getMessage(), error);
                //textView.setText("error");
            }
        });
        requestQueue.add(stringRequest);
    }*/
    /*public static void sendHttprequest(final String address, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection connection = null;
                try{
                    URL url = new URL(address);
                    connection = (HttpsURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    if (listener != null){
                        listener.onFinish(response.toString());
                    }
                }catch (Exception e){
                    if (listener != null){
                        listener.onError(e);
                    }
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }*/
    /*public void myClickHandler(View v){
        String url = editText.getText().toString();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()){
            new DownloadwebpageText().execute(url);
        }else{

        }
    }
    private String downloadUrl(String myurl) throws Exception{
        InputStream is = null;
        int len = 500;
        try{
            URL url = new URL(myurl);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(DEBUG_TAG, "response is " + response);
            is = connection.getInputStream();
            String ContentAsString = readit(len, is);
            return ContentAsString;
        }finally {
            if (is != null){
                is.close();
            }
        }
    }
    private String readit(int len, InputStream is)throws IOException, UnsupportedEncodingException{
        Reader reader = null;
        reader = new InputStreamReader(is ,"UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
    private class DownloadwebpageText extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... url){
            try {
                return downloadUrl(url[0]);
            }catch (Exception e){
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        @Override
        protected void onPostExecute(String result){
            response.setText(result);
        }
    }*/


}
