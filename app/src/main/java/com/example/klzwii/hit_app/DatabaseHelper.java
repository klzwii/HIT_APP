package com.example.klzwii.hit_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String NAME="message.db";
    private static final int version=1;

    public DatabaseHelper(Context context) {
        super(context, NAME, null, version);
        // TODO Auto-generated constructor stub
    }
    public DatabaseHelper(Context context, String name, CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        System.out.println("wotmkuaisile");
        db.execSQL("CREATE TABLE message (time integer primary key autoincrement,message text,type integer,target text )");
        db.execSQL("CREATE TABLE clas (id integer primary key autoincrement,name text)");
        db.execSQL("CREATE TABLE quiz (starttime integer primary key autoincrement,type integer,expiredtime integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE if exists message");
        onCreate(db);
    }

}