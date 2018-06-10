package com.example.klzwii.hit_app;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;

public class get_pas extends Thread{
    String name = null;
    private Context context = null;
    public get_pas(String name,Context context){
        this.name = name;
        this.context=context;
    }

    @Override
    public void run() {
        try{
            SQLiteOpenHelper dbhelper = new DatabaseHelper(context);
            SQLiteDatabase db=dbhelper.getWritableDatabase();
            Cursor cursor =db.rawQuery("select id from clas where name = '"+name+"'",null);
            cursor.moveToFirst();
            int id=cursor.getInt(0);
            cursor.close();
            db.close();
            dbhelper.close();
            String pass_wd=conn_claspas.log(data.getHit_num(),data.getAndroid_id(),id);
            Intent intent = new Intent(context,clas_login.class);
            intent.putExtra("pass_wd",pass_wd);
            intent.putExtra("id",id);
            context.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
