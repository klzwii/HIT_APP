package com.example.klzwii.hit_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class clas_om extends AppCompatActivity {

    private static AlertDialog dialog=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clas_om);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fabb);
        AlertDialog.Builder builder = new AlertDialog.Builder(clas_om.this);
        builder.setTitle("发言");    //设置对话框标题
        builder.setIcon(android.R.drawable.btn_star);
        final EditText edit = new EditText(clas_om.this);
        builder.setView(edit);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(clas_om.this, "你输入的是: " + edit.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(clas_om.this, "你点了取消", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarr);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if (dialog!=null)
            dialog.dismiss();
    }
}
