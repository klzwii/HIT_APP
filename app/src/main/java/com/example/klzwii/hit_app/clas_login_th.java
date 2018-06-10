package com.example.klzwii.hit_app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import cn.edu.thu.cv.util.Base64Image;

public class clas_login_th extends Thread{
    private static final String IP_ADDR = "23.106.155.19";//服务器地址  这里要改成服务器的ip
    private static final int PORT = 12345;//服务器端口号
    private String c;
    private int id;
    private Double lox;
    private Double loy;
    private Context mcontext;
    clas_login_th(String c, Double lox, Double loy, Context mcontext,int id){
        this.id=id;
        this.c=c;
        this.lox=lox;
        this.loy=loy;
        this.mcontext=mcontext;
    }
    @Override
    public void run() {
        EventBus.getDefault().register(this);
        String a=data.getHit_num();
        String imgStr = Base64Image.GetImageStr(c);
        Map<String,String> mapp=new HashMap<String, String>();
        mapp.put("status","0");
        JSONObject jss=new JSONObject(mapp);
        while (true) {
            Socket socket = null;
            try {
                //创建一个流套接字并将其连接到指定主机上的指定端口号
                socket = new Socket(IP_ADDR, PORT);
                System.out.println("连接已经建立");
                //向服务器端发送数据
                Map<String, String> map = new HashMap<String, String>();
                map.put("op","5");
                map.put("img", imgStr);
                map.put("hit_num",a);
                map.put("auth_key",data.getAndroid_id());
                //将json转化为String类型
                JSONObject json = new JSONObject(map);
                json.put("lat",lox);
                json.put("lot",loy);
                json.put("id",id);
                String jsonString = "";
                jsonString = json.toString();
                Log.i("tag",json.getString("lat")+" "+json.getString("lot"));
                //将String转化为byte[]
                //byte[] jsonByte = new byte[jsonString.length()+1];
                byte[] jsonByte = jsonString.getBytes();
                DataOutputStream outputStream = null;
                outputStream = new DataOutputStream(socket.getOutputStream());
                System.out.println("发的数据长度为:"+jsonByte.length);
                outputStream.write(jsonByte);
                outputStream.flush();
                System.out.println("传输数据完毕");
                socket.shutdownOutput();

                //读取服务器端数据
                DataInputStream inputStream = null;
                String strInputstream ="";
                inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                strInputstream=inputStream.readUTF();
                System.out.println("输入信息为："+strInputstream);
                JSONObject js = new JSONObject(strInputstream);
                // 如接收到 "OK" 则断开连接
                if (js != null) {
                    if(js.getString("status").equals("1")){
                        Intent intent = new Intent(mcontext,clas_om.class);
                        mcontext.startActivity(intent);
                        EventBus.getDefault().post(new message_event("end_log"));
                    }
                    System.out.println("客户端将关闭连接");
                    break;
                }
            } catch (Exception e) {
                System.out.println("客户端异常:" + e.getMessage());
                break;
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        socket = null;
                        System.out.println("客户端 finally 异常:" + e.getMessage());
                    }
                }
            }
        }
        EventBus.getDefault().post(new message_event("end"));
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void Event(message_event messageEvent) {
    }
}
