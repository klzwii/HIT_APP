package com.example.klzwii.hit_app;

import net.coobird.thumbnailator.Thumbnails;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;


import com.nanchen.compresshelper.CompressHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
public class jap_resize {
    public static File compressBmpToFile(String FILE_PATH, Context mcontext) {
        File file = new File(FILE_PATH);

        File newfile =null;
        try {
             newfile = CompressHelper.getDefault(mcontext).compressToFile(file);
            Log.i("asasda",newfile.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }
        return newfile;
    }
}
