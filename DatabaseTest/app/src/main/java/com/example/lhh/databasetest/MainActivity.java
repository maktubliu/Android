package com.example.lhh.databasetest;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class MainActivity extends Activity {

    private MyDataBaseHelper dbhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelper = new MyDataBaseHelper(this, "BookStore.db", null, 1);
        Button create_database = (Button) findViewById(R.id.create_database);
        Button add_data = (Button) findViewById(R.id.add_data);
        Button update_data = (Button) findViewById(R.id.update_data);
        Button delete_data = (Button) findViewById(R.id.delete_data);
        Button query_data = (Button) findViewById(R.id.query_data);
        Button replace_data = (Button) findViewById(R.id.replace_data);
        create_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbhelper.getWritableDatabase();
            }
        });
        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                db.beginTransaction();
                ContentValues values = new ContentValues();
                values.put("name", "Fuck you!");
                values.put("author", "liuce");
                values.put("pages", 999);
                values.put("price", 99.9);
                db.insert("Book", null, values);
                values.clear();
                values.put("name", "Boom Shakalaka");
                values.put("author", "guaiguai");
                values.put("pages", 1000000);
                values.put("price", 999999);
                db.insert("Book", null, values);
            }
        });
        update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 100000);
                db.update("Book", values, "name = ?", new String[]{"Fuck you!"});
            }
        });
        delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                db.delete("Book", "pages > ?", new String[]{"500"});//指定删除大于500的列
            }
        });
        query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getReadableDatabase();
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if(cursor.moveToFirst()){
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        Log.d("MainActivity", "book name is" + name);
                        Log.d("MainActivity", "book author is" + author);
                        Log.d("MainActivity", "book pages is" + pages);
                        Log.d("MainActivity", "book price is" + price);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });
        replace_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbhelper.getWritableDatabase();
                db.beginTransaction();
                try{
                    db.delete("Book", null, null);
                    /*if(true){
                        throw new NullPointerException();
                    }*/
                    ContentValues values = new ContentValues();
                    values.put("name", "Boomshakalaka");
                    values.put("author", "mingming");
                    values.put("pages", 520);
                    values.put("price", 5.20);
                    db.insert("Book", null, values);
                    db.setTransactionSuccessful();
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally {
                    db.endTransaction();
                }
            }
        });
    }

}
