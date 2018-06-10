package cn.edu.thu.cv.util;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.util.Base64;
public class Base64Image
{
    public static String GetImageStr(String imapath)
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imapath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //对字节数组Base64编码
        String strBase64 = Base64.encodeToString(data, Base64.DEFAULT);
        return strBase64;//返回Base64编码过的字节数组字符串
    }
}