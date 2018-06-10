package com.example.klzwii.hit_app;

import android.app.MediaRouteActionProvider;
import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class data {
    private static String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/user_info.json";
    public static void new_ins(String a,String b){
        File file = new File(FILE_PATH);
        file.mkdirs();
        if(file.exists())
            file.delete();
        Map<String,String > map = new HashMap<String, String>();
        map.put("hit_num",a);
        map.put("auth_key",b);
        JSONObject json = new JSONObject(map);
        String js=json.toString();
        try {
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(js.getBytes());
            fout.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String getHit_num(){
        File file = new File(FILE_PATH);
        if(!file.exists())
            return null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            String temp;
            String result = "";
            while((temp=br.readLine())!=null)
                result+=temp;
            br.close();
            JSONObject json = new JSONObject(result);
            String k= json.getString("hit_num");
            return k;
        }catch (Exception e){
            return null;
        }
    }
    public static String getAndroid_id(){
        File file = new File(FILE_PATH);
        if(!file.exists())
            return null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
            String temp;
            String result = "";
            while((temp=br.readLine())!=null)
                result+=temp;
            br.close();
            JSONObject json = new JSONObject(result);
            String k= json.getString("auth_key");
            return k;
        }catch (Exception e){
            return null;
        }
    }
}
