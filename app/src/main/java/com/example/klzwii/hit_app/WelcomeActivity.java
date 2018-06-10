package com.example.klzwii.hit_app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private String TAG ="perimission_request";

    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    List<String> mPermissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_welcome);
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(WelcomeActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了

        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(WelcomeActivity.this, permissions, 2);
        }
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(WelcomeActivity.this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if(!mPermissionList.isEmpty()){
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }/*
        Intent intent = new Intent(WelcomeActivity.this,asd.class);
        WelcomeActivity.this.startActivity(intent);
        WelcomeActivity.this.finish();*/
       new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                }catch (Exception e) {
                    e.printStackTrace();
                }
                Intent mainIntent = null;
                if(data.getAndroid_id()!=null){
                    String k=token_auth.get_auth();
                    if(k.equals("right")){
                        mainIntent=new Intent(WelcomeActivity.this,asd.class);
                    }
                }
                if(mainIntent==null)
                    mainIntent = new Intent(WelcomeActivity.this,log_signActivity.class);
                WelcomeActivity.this.startActivity(mainIntent);
                WelcomeActivity.this.finish();
            }
        }).start();
        }
    }

