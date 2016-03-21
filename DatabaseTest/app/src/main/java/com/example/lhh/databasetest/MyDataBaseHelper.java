package com.example.lhh.databasetest;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by LHH on 2016/3/1.
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_BOOK = "create table Book(" + "id integer primary key autoincrement," +
            "author text," + "price real," + "pages integer," + "name text," + "category_id integer)";
    public static final String CREATE_CATEGORY = "create table Category(" + "id integer primary key autoincrement," +
            "category_name text," + "category_code integer)";
    //private Context mContext;
    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        //mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        //Toast.makeText(mContext, "success create database", Toast.LENGTH_SHORT).show();跨程序无法使用makeText
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //db.execSQL("drop table if exists Book");
        //db.execSQL("drop table if exists Category");
        //onCreate(db);
        switch (oldVersion){
            case(1):
                db.execSQL(CREATE_CATEGORY);
            case(2):
                db.execSQL("alter table Book column category_id integer");
            default:
        }
    }
}
