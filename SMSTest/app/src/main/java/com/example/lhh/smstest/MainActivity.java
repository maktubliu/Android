package com.example.lhh.smstest;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private IntentFilter receiverFilter;
    private IntentFilter sendFilter;
    private MessageReceiver messageReceiver;
    private SendStatusReceiver sendStatusReceiver;
    private TextView sender;
    private TextView content;
    private EditText to;
    private EditText msginput;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sender = (TextView) findViewById(R.id.sender);
        content = (TextView) findViewById(R.id.content);
        setContentView(R.layout.activity_main);
        receiverFilter = new IntentFilter();
        receiverFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        receiverFilter.setPriority(100);
        messageReceiver = new MessageReceiver();
        registerReceiver(messageReceiver, receiverFilter);
        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        sendStatusReceiver = new SendStatusReceiver();
        registerReceiver(sendStatusReceiver, sendFilter);
        //receiverFilter = new MessageReceiver();
        to = (EditText) findViewById(R.id.to);
        send = (Button) findViewById(R.id.send_button);
        msginput = (EditText) findViewById(R.id.msg_input);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smsManager = SmsManager.getDefault();
                Intent sentintent = new Intent("SENT_SMS_ACTION");
                PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, 0, sentintent, 0);
                smsManager.sendTextMessage(to.getText().toString(), null, msginput.getText().toString(), pi, null);
            }
        });
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(messageReceiver);
        unregisterReceiver(sendStatusReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class MessageReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){
            Bundle bundle = intent.getExtras();
            Object[] pdus = (Object[]) bundle.get("pdus");//pdus
            SmsMessage[] smsMessage = new SmsMessage[pdus.length];
            for (int i = 0; i < smsMessage.length; i++){
                smsMessage[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
            }
            String address = smsMessage[0].getOriginatingAddress();
            String fullmessage = "";
            for(SmsMessage message : smsMessage){
                fullmessage += message.getMessageBody();
            }
            sender.setText(address);
            content.setText(fullmessage);
            abortBroadcast();
        }
    }
    class SendStatusReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent){
            if(getResultCode() == RESULT_OK){
                Toast.makeText(context, "Send success", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Send failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
