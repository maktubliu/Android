package com.example.lhh.networktest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.lhh.networktest.fragment.XmlRequestFragment;
import com.example.lhh.networktest.util.Constans;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button send_request;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       findViewById(R.id.send_request).setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        Intent intent = new Intent(MainActivity.this, RequestActivity.class);
        switch (v.getId()){
            case R.id.send_request:
                intent.putExtra(Constans.Extra.FRAGMENT_INDEX, XmlRequestFragment.INDEX);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
    /*private Button send_resquest;
    private TextView request;
    private static final int SHOW_RESPONSE = 0;
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_RESPONSE:
                    String response = (String)msg.obj;
                    request.setText(response);
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send_resquest = (Button) findViewById(R.id.send_request);
        request = (TextView) findViewById(R.id.request_response);
        send_resquest.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(v.getId() == R.id.send_request){
            SendRequestWithHttpConnection();
        }
    }
    private void SendRequestWithHttpConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                //HttpsURLConnection connection = null;
                try{
                    URL url = new URL("http://m.hupu.com");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    //connection.setDoInput(true);
                    //connection.setDoOutput(true);
                    //connection.setUseCaches(false);
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    //connection.connect();
                    //DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                    //String params = URLEncoder.encode("name = 123456", "gb2312");
                    //outputStream.write(params.getBytes());
                    //outputStream.flush();
                    //outputStream.close();
                    InputStream inputStream = connection.getInputStream();
                    //对获取的数据流进行读取
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }//HttpsURLconnection连接
                    /*HttpClient httpClient = new DefaultHttpClient();*/
                    /*Message message = new Message();
                    message.what = SHOW_RESPONSE;
                    //将服务器返回结果存在message中
                    message.obj =response.toString();
                    handler.sendMessage(message);
                    //reader.close();
                    //inputStream.close();

                }/*catch (MalformedURLException e){
                    e.printStackTrace();
                }*/
                /*catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }*/
}
