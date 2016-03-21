package com.example.lhh.broadcastbestpractice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by LHH on 2016/3/1.
 */
public class MYDataBaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_BOOK = "create table book (" + "id integer primary key autoincrement," +
            "author text," + "price real," + "pages integer," + "name text)";
    private Context mContext;
    public MYDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_BOOK);
        Toast.makeText(mContext, "success create database", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
    }

}
