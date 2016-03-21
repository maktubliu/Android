package com.example.listviewtest;

import java.util.ArrayList;
import android.view.*;

import java.util.List;

import android.app.Activity;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;


public class MainActivity extends Activity {

	//private String[] data = {"Apple","Banana", "Orange", "WaterMelon", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};
    private List<Fruit> fruitList = new ArrayList<Fruit>();
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(
        	MainActivity.this, android.R.layout.simple_list_item_1, data);
        ListView listview = (ListView)findViewById(R.id.list_view);
        listview.setAdapter(adapter);*/
        initFruits();
        ArrayAdapter<Fruit> adapter = new ArrayAdapter<Fruit>(MainActivity.this,R.layout.fruit_item,fruitList);
        ListView listview = (ListView)findViewById(R.id.list_view);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener(){
        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id){
        		Fruit fruit = fruitList.get(position);
        		Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
        		
        	}
        	
        });
    }

	private void initFruits(){
		Fruit apple = new Fruit("Apple", R.drawable.ic_launcher);
		fruitList.add(apple);
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
