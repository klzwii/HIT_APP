package com.example.klzwii.hit_app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
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

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.net.URI;
public class regiActivity extends AppCompatActivity {

    private static  String FILE_PATH = "/sdcard/syscamera.jpg";
    private static final String TAG = "main";
    private final MyHandler handler = new MyHandler(this);
    private static ZLoadingDialog dialog=null;
    private static class MyHandler extends Handler {
        private final WeakReference<regiActivity> mActivity;

        public MyHandler(regiActivity activity) {
            mActivity = new WeakReference<regiActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            regiActivity activity = mActivity.get();
            if (activity != null) {
                super.handleMessage(msg);
                dialog.cancel();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi);
        Button camera_button = (Button) findViewById(R.id.cambutton);
        camera_button.setOnClickListener(click);
        Button sign_button = findViewById(R.id.button2);
        sign_button.setOnClickListener(cliick);
    }
    private View.OnClickListener click = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = null;
            SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
            String filename=formatter.format(new Date());
            FILE_PATH=Environment.getExternalStorageDirectory().getAbsolutePath()+"/gallery/"+formatter.format(new Date())+".jpg";
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
                uri = FileProvider.getUriForFile(regiActivity.this, regiActivity.this.getApplicationContext().getPackageName() + ".provider",file );
                //做一些处理
            } else{
                uri = Uri.fromFile(file);
            }

            // 设置系统相机拍摄照片完成后图片文件的存放地址
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, 0);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "系统相机拍照完成，resultCode="+resultCode);
        File file = new File(FILE_PATH);
        if (requestCode == 0&&file.exists()) {
            file=jap_resize.compressBmpToFile(FILE_PATH,regiActivity.this);
            FILE_PATH=file.getAbsolutePath();
            Uri uri = Uri.fromFile(file);
            ImageView asd= findViewById(R.id.imageView);
            asd.setImageURI(uri);
            Button came = (Button) findViewById(R.id.cambutton);
            came.setText("重拍");
        }
    }
    private View.OnClickListener cliick = new View.OnClickListener() {
        @Override
        public void onClick(View v)     {
            dialog=new ZLoadingDialog(regiActivity.this);
            dialog.setLoadingBuilder(Z_TYPE.SNAKE_CIRCLE).setLoadingColor(Color.BLACK).setHintText("Loading...").setHintTextSize(16);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            new Thread(new Runnable() {
                public void run() {
                    String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
                    File file =new File(FILE_PATH);
                    EditText uste=findViewById(R.id.reus);
                    String ustes = uste.getText().toString();
                    EditText pste=findViewById(R.id.editText3);
                    String pss=pste.getText().toString();
                    if(file.exists()&&!ustes.equals("")&&!pss.equals("")){
                        pss=MD5Util.getMD5(pss);
                        int k=register.regist(ustes,pss,FILE_PATH,ANDROID_ID);
                        if(k==1){
                            if(file.exists())
                                file.delete();
                            Intent mainIntent = new Intent(regiActivity.this,log_signActivity.class);
                            regiActivity.this.startActivity(mainIntent);
                            regiActivity.this.finish();

                        }
                        else
                            Log.i("main","something_happened");
                    }
                    Message ok = new Message();
                    handler.sendMessage(ok);
                }
            }).start();


        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销订阅者
        if(dialog!=null)
            dialog.dismiss();
    }
}
