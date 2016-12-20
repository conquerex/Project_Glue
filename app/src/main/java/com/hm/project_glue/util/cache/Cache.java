package com.hm.project_glue.util.cache;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by HM on 2016-12-19.
 */

public class Cache {
    Context context;
    public Cache(Context context){
        this.context = context;
    }

    public File getCacheDir(Context context) {
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

    public void Write(String path, int number) throws IOException {
        File cacheDir = getCacheDir(context);
        File cacheFile = new File(cacheDir, "photo"+number+".jpg");
        if(!cacheFile.exists())cacheFile.createNewFile();
        FileWriter fileWriter = new FileWriter(cacheFile);
        fileWriter.write(path);
        fileWriter.flush();
        fileWriter.close();
    }

    public String Read(int number) throws IOException {
        File cacheDir = getCacheDir(context);
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