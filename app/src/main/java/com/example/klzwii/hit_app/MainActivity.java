package com.example.klzwii.hit_app;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Trace;

import com.zyao89.view.zloading.ZLoadingBuilder;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import java.lang.ref.WeakReference;

public class MainActivity extends Activity {

    private final MyHandler handler = new MyHandler(this);
    private static ZLoadingDialog dialog=null;
    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                super.handleMessage(msg);
                dialog.dismiss();
            }
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog=new ZLoadingDialog(MainActivity.this);
        dialog.setLoadingBuilder(Z_TYPE.SNAKE_CIRCLE).setLoadingColor(Color.BLACK).setHintText("Loading...").setHintTextSize(16);
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(10000);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Message ok = new Message();
                handler.sendMessage(ok);
            }
        }).start();

    }

}
