package com.example.lhh.providertest;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
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

public class MainActivity extends AppCompatActivity {

    private String newId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button add_data = (Button) findViewById(R.id.add_data);
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.lhh.provider/Book");
                ContentValues values = new ContentValues();
                values.put("name", "Fuck you everyday");
                values.put("author", "me");
                values.put("pages", 100);
                values.put("price", 520);
                Uri newUri = getContentResolver().insert(uri, values);
                newId = newUri.getPathSegments().get(1);
            }
        });
        Button query_data = (Button) findViewById(R.id.query_data);
        query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.lhh.provider/Book");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if(cursor != null){
                    while(cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "book name is" + name);
                        Log.d("MainActivity", "book author is" + author);
                        Log.d("MainActivity", "book pages is" + pages);
                        Log.d("MainActivity", "book price is" + price);
                    }
                    cursor.close();
                }
            }
        });
        Button update_data = (Button) findViewById(R.id.updata_data);
        update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.lhh.provider/Book" + newId);
                ContentValues values = new ContentValues();
                values.put("name", "shit!");
                values.put("author", "guaiguai");
                values.put("pages", 110);
                values.put("price", 0.1);
                getContentResolver().update(uri, values, null, null);
            }
        });
        Button delete_data = (Button) findViewById(R.id.delete_data);
        delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.lhh.provider/Book/" + newId);
                getContentResolver().delete(uri, null, null);
            }
        });

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
}
