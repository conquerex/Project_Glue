package com.hm.project_glue.util.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by HM on 2016-12-19.
 */

public class Cache {
    Context context;
    public Cache(Context context){
        this.context = context;
    }

    public File getCacheDir() {
        File cacheDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "mycachefolder");
            if(!cacheDir.isDirectory()) {
                cacheDir.mkdirs();
            }
        }
        if(!cacheDir.isDirectory()) {
            cacheDir = context.getCacheDir();
        }
        return cacheDir;
    }
    public Bitmap imgReSizing(String path){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int reqWidth = dm.widthPixels;
        int reqHeight = dm.heightPixels;
        String imagePath = path;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = null;
        try{
            BitmapFactory.decodeStream(new FileInputStream(imagePath), null, options);
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;
            Log.i( TAG, "imagePath:"+imagePath);
            Log.i( TAG, "reqWidth:"+reqWidth+"/reqHeight:"+reqHeight+"/height:"+height+"/width:"+width);
            if (height > reqHeight || width > reqWidth) {
                options.inSampleSize = (width / reqWidth);
            }
            Log.i( TAG, "inSampleSize:"+inSampleSize);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(imagePath, options);


        }catch (Exception e ){
            Log.e(TAG, e.getMessage());
        }
        return bitmap;

    }
    public void write(String path, int number) throws IOException {

        try{
            Bitmap bitmap = imgReSizing(path);
            File cacheDir = getCacheDir();
            File cacheFile = new File(cacheDir, "photo"+number+".jpg");
            FileOutputStream out = new FileOutputStream(cacheFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

        }catch(FileNotFoundException exception){
            Log.e("FileNotFoundException", exception.getMessage());
        }catch(IOException exception){
            Log.e("IOException", exception.getMessage());
        }

    }

    public String Read(int number) throws IOException {
        File cacheDir = getCacheDir();
        File cacheFile = new File(cacheDir,  "photo"+number+".jpg");
        if(!cacheFile.exists())cacheFile.createNewFile();
        FileInputStream inputStream = new FileInputStream(cacheFile);
        Scanner s = new Scanner(inputStream);
        String text="";
        while(s.hasNext()){
            text+=s.nextLine();
        }
        inputStream.close();
        return text;
    }

}