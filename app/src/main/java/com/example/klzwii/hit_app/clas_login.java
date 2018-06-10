package com.example.klzwii.hit_app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class clas_login extends AppCompatActivity {
    private static final String TAG ="clas_login";
    private static  String FILE_PATH = "/sdcard/syscamera.jpg";
    private static ZLoadingDialog dialog=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clas_login);
        String asd=getIntent().getStringExtra("pass_wd");
        EventBus.getDefault().register(this);
        Button Cambutton = findViewById(R.id.cambutton4);
        Cambutton.setOnClickListener(click);
        Button okButton = findViewById(R.id.button5);
        okButton.setOnClickListener(clickk);
    }
    private View.OnClickListener click = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
            String filename=formatter.format(new Date());
            FILE_PATH= Environment.getExternalStorageDirectory().getAbsolutePath()+"/gallery/"+formatter.format(new Date())+".jpg";

            intent = new Intent();
            // 指定开启系统相机的Action
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            //intent.addCategory(Intent.CATEGORY_DEFAULT);
            // 根据文件地址创建文件
            File file = new File(FILE_PATH);
            if (file.exists()) {
                file.delete();
            }
            file.getParentFile().mkdirs();
            // 把文件地址转换成Uri格式
            Uri uri=null;
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(clas_login.this, clas_login.this.getApplicationContext().getPackageName() + ".provider",file );
                //做一些处理
            } else{
                uri = Uri.fromFile(file);
            }

            // 设置系统相机拍摄照片完成后图片文件的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 0);
        }
    };
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(message_event messageEvent) {
        if(messageEvent.getMessage().equals("end"))
            dialog.cancel();
        if(messageEvent.getMessage().equals("end_log"))
            clas_login.this.finish();
    }
    public View.OnClickListener clickk = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText cla_pas = findViewById(R.id.editText5);
            String clas_pa = cla_pas.getText().toString();
            cla_pas.setLongClickable(false);
            cla_pas.setTextIsSelectable(false);
            if(!new File(FILE_PATH).exists())
                return;
            if(clas_pa.equals(getIntent().getExtras().get("pass_wd"))){
                Location location=null;
                if(clas_login.this.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION")== PackageManager.PERMISSION_GRANTED){// 获取的是位置服务
                    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    String Gproviders = LocationManager.GPS_PROVIDER;
                    String Nproviders = LocationManager.NETWORK_PROVIDER;
                    if (locationManager != null) {
                        LocationProvider Gprovider = locationManager.getProvider(Gproviders);
                        LocationProvider Nprovider = locationManager.getProvider(Nproviders);
                        if(Gprovider!=null){
                            location = locationManager.getLastKnownLocation(Gproviders);
                        }
                        if (location==null)
                            location = locationManager.getLastKnownLocation(Nproviders);
                    }
                }
                else {
                    Toast.makeText(clas_login.this,"cannnot access to location service",Toast.LENGTH_SHORT).show();
                }
                if (location!=null){
                    dialog=new ZLoadingDialog(clas_login.this);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setLoadingBuilder(Z_TYPE.SNAKE_CIRCLE).setLoadingColor(Color.BLACK).setHintText("Loading...").setHintTextSize(16);
                    dialog.show();
                    clas_login_th th = new clas_login_th(FILE_PATH,location.getLatitude(),location.getLongitude(),clas_login.this,getIntent().getExtras().getInt("id"));
                    th.start();
                }
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "系统相机拍照完成，resultCode="+resultCode);
        File file = new File(FILE_PATH);
        if (requestCode == 0&&file.exists()) {
            file=jap_resize.compressBmpToFile(FILE_PATH,clas_login.this);
            FILE_PATH=file.getAbsolutePath();
            Uri uri = Uri.fromFile(file);
            ImageView asd= findViewById(R.id.imageView2);
            asd.setImageURI(uri);
            Button came = (Button) findViewById(R.id.cambutton4);
            came.setText("重拍");
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销订阅者
        if(dialog!=null)
            dialog.dismiss();
        EventBus.getDefault().unregister(this);
    }
}
