package com.hm.project_glue.main.msg;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hm.project_glue.main.msg.data.NotiData;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by HM on 2016-12-21.
 */

public class DBinit {
    static Context context;
    String targetFile;
    private static String fileName;
    DBinit(Context context){
        this.context = context;
        init();
    }
    private void init(){
        fileName = "noti.sqlite";
        File file = new File(getFullpath(fileName));
        if(!file.exists()){
            assetToDisk(fileName);
        }
    }
    public static String getFullpath(String filename){
        return context.getFilesDir().getAbsolutePath() + File.separator + filename;
    }
    public static SQLiteDatabase openDb(){
        return  SQLiteDatabase.openDatabase(getFullpath(fileName),null,0);
    }
    public void assetToDisk(String filename){
        // 외부에서 작성된 sqlite db 파일 사용하기
        // 1. assets 에 담아둔 파일을 internal 혹은 external 공간으로 복사한다.

        InputStream is = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            AssetManager manager = context.getAssets();
            is = manager.open(filename);
            bis = new BufferedInputStream(is);

            // 2. 저장할 위치에 파일이 없으면 생성해둔다.
            targetFile = getFullpath(fileName);
            Log.i(TAG, targetFile);

            File file = new File(targetFile);

            if(!file.exists()){
                file.createNewFile();
            }

            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            // 읽어올 데이터를 담아줄 변수
            int read = -1; // 모두 읽어오면 -1이 리턴
            // 한번에 읽은 버퍼의 크기를 지정
            byte buffer[] = new byte[1024];
            // 더 이상 읽어올 데이터가 없을때까지 buffer 단위로 읽어서 쓴다.
            while((read = bis.read(buffer, 0 ,1024)) != -1){
                bos.write(buffer, 0, read);
            }
            bos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) bos.hashCode();
                if (fos != null) fos.hashCode();
                if (bis != null) bis.hashCode();
                if (is != null) is.hashCode();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 입력
    public static void dbInsert(NotiData data) {

        SQLiteDatabase db = null;
        try {
            db = openDb();
            if (db != null) {
                db.execSQL("insert into noti(title,contents) values('"+data.getTitle()+"','"+data.getContents()+"')");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (db != null) db.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public static ArrayList<NotiData> dbSelect() {

        ArrayList<NotiData> datas = new ArrayList<>();
        SQLiteDatabase db=null;
        try{
            db = openDb();
            if (db != null) {
                Cursor cursor = db.rawQuery("select title,contents from noti order by rowid desc", null);
                while (cursor.moveToNext()) {
                    NotiData data = new NotiData();
                    int idx = cursor.getColumnIndex("title");
                    data.setTitle(cursor.getString(idx));
                    idx = cursor.getColumnIndex("contents");
                    data.setContents(cursor.getString(idx));
                    datas.add(data);

                }
            }
        }catch (Exception e){

        }



        return datas;
    }

}
