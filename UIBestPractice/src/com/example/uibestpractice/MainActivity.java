package com.example.uibestpractice;

import java.util.ArrayList;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View.OnClickListener;

public class MainActivity extends Activity{
	private ListView msgListView;
	private Button send;
	private EditText inputText;
	private MsgAdapter adapter;
	private List<Msg> Msglist = new ArrayList<Msg>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initMsgs();
        adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, Msglist);
        msgListView = (ListView) findViewById(R.id.msg_list_view);
        send = (Button) findViewById(R.id.send);
        inputText = (EditText) findViewById(R.id.input_text);
        msgListView.setAdapter(adapter);
        send.setOnClickListener(new OnClickListener(){
        	@Override
        	public void onClick(View v){
        		String content = inputText.getText().toString();
        		if(!"".equals(content)){
        			Msg msg = new Msg(content, Msg.TYPE_SENT);
        			Msglist.add(msg);
        			adapter.notifyDataSetChanged();//当有状态改变时则刷新ListView的状态
        			msgListView.setSelection(Msglist.size());//将ListView定位到最后一行
        			inputText.setText("");
        		}
        	}
        });
    }
    private void initMsgs(){
    	Msg msg1 = new Msg("Hello!", Msg.TYPE_RECEIVED);
    	Msglist.add(msg1);
    	Msg msg2 = new Msg("You are SB!", Msg.TYPE_SENT);
    	Msglist.add(msg2);
    	Msg msg3 = new Msg("FUCK!", Msg.TYPE_RECEIVED);
    	Msglist.add(msg3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
