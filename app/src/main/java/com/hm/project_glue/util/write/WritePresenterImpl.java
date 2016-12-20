package com.hm.project_glue.util.write;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;

import com.hm.project_glue.main.home.data.HomeData;
import com.hm.project_glue.main.timeline.data.Posts;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.util.cache.Cache;
import com.hm.project_glue.util.http.ListRestAdapter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HM on 2016-12-13.
 */

public class WritePresenterImpl implements WritePresenter {
    private final static String TAG = "TEST";
    WritePresenter.View view;
    Context context;
    public WritePresenterImpl(Context context){
        this.context = context;
//        groupListCallHttp();

    }

//    @Path("groupId") String groupId,
//    @Field("content") String content,
//    @Field("group") String group,
//    @PartMap() Map<String, RequestBody> partMap

    private byte[] imgResizeBitmap(String path){
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int reqWidth = dm.widthPixels;
        int reqHeight = dm.heightPixels;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 4;
        byte[] byteArray=null;
        Bitmap bitmap;
        Cache cache = new Cache(context);

        try{
            BitmapFactory.decodeStream(new FileInputStream(path), null, options);
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;
            Log.i( TAG, "imagePath:"+path);
            Log.i( TAG, "reqWidth:"+reqWidth+"/reqHeight:"+reqHeight+"/height:"+height+"/width:"+width);
            if (height > reqHeight || width > reqWidth) {
                options.inSampleSize = (width / reqWidth);
            }
            Log.i( TAG, "inSampleSize:"+inSampleSize);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(path, options);
            ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
            byteArray = stream.toByteArray();

        }catch (Exception e ){
            Log.e(TAG, e.getMessage());
        }


        return byteArray;
    }

    @Override
    public void httpPosting(ArrayList<String> photosPath, String groupId, String content){
            Log.i(TAG, "httpPosting");

//        new AsyncTask<String, Void, Integer>(){
//            String boundary="----------";
//            String lineEnd = "\r\n";
//            String twoHyphens = "--";
//            int maxBufferSize = 1 * 1024 * 1024;
//            int bufferSize;
//            public static final int MAX_READ_TIME = 10000;
//            public static final int MAX_CONNECT_TIME = 15000;
//            int responseCode = 0;
//
//            @Override
//            protected Integer doInBackground(String... params) {
//                int responeCode = 0;
//
//                try {
//                    URL connectUrl = new URL(Networking.getBASE_URL() + "/posts/post_list/" + groupId + "/");
//                    HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
//                    conn.setDoOutput(true);
//                    conn.setDoInput(true);
//                    conn.setRequestMethod("POST");
//                    conn.setRequestProperty("Connection", "Keep-Alive");
//                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
//                    conn.setRequestProperty("Connection", "Keep-Alive");
//                    conn.setRequestProperty("Content-Type","multipart/form-data; boundary="+boundary);
//                    conn.setRequestProperty("Authorization", "Token "+Networking.getToken());
//                    conn.setRequestProperty("cache-control", "no-cache");
//                    conn.connect();
//
//                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
//
//                    dos.writeBytes("\r\n--" + boundary + "\r\n");
//                    dos.writeBytes("Content-Disposition: form-data; name=\"content\"\r\n\r\n" + content);
//                    dos.writeBytes("\r\n--" + boundary + "\r\n");
//                    dos.writeBytes("Content-Disposition: form-data; name=\"group\"\r\n\r\n" + groupId);
//
//                    //photo가 있으면
//                    for(String photopath : photosPath){
//
//                        Log.i(TAG,"for / photopath:"+photopath);
//                        dos = new DataOutputStream(conn.getOutputStream());
//                        File file = new File(photopath);
//                        byte[] pixels;
//                        if(file.exists()){
//                            // 이미지 압축
////                            BitmapFactory.Options option = new BitmapFactory.Options();
////                            if (file.length() > 100000)
////                                option.inSampleSize = 10;
////                            else
////                                option.inSampleSize = 2;
////                            Bitmap bitmap = BitmapFactory.decodeFile(photopath, option);
//                            Bitmap bitmap = BitmapFactory.decodeFile(photopath);
//                            pixels = new byte[bitmap.getWidth() * bitmap.getHeight()];
//                            for (int i = 0; i < bitmap.getWidth(); ++i) {
//                                for (int j = 0; j < bitmap.getHeight(); ++j) {
//                                    pixels[i + j] = (byte) ((bitmap.getPixel(i, j) & 0x80) >> 7);
//                                }
//                            }
//
//                            FileInputStream mFileInputStream = new FileInputStream(file);
//
//                            dos.writeBytes("\r\n--" + boundary + "\r\n");
//                            dos.writeBytes("Content-Disposition: form-data; name=\"photos\"; filename=\"" + "img.jpg" + "\"" + lineEnd);
////                            dos.writeBytes("Content-Type: application/octet-stream" + lineEnd);
//                            dos.writeBytes(lineEnd);
//                            int bytesAvailable = mFileInputStream.available();
//                            int bytesRead = mFileInputStream.read(pixels, 0, bufferSize);
//                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                            while (bytesRead > 0)
//                            {
//                                Log.i(TAG, "bytesRead: "+bytesRead);
//                                dos.write(pixels, 0, bufferSize);
//                                bytesAvailable = mFileInputStream.available();
//                                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                                bytesRead = mFileInputStream.read(pixels, 0, bufferSize);
//                            }
//
//                            mFileInputStream.close();
//                        }
//
//
//                    }
//
//                    dos.writeBytes(lineEnd);
//                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//                    dos.flush();
//                    dos.close();
//                    if(conn.getResponseCode() == 200 ||conn.getResponseCode() == 201 ){
//                        Log.i(TAG,"conn.getResponseCode(): "+conn.getResponseCode() );
//                        responeCode = conn.getResponseCode() ;
//                    }else{
//                        Log.i(TAG,"conn.getResponseCode(): "+conn.getResponseCode() );
//                        responeCode = conn.getResponseCode() ;
//                    }
//                }catch(Exception e){
//                    Log.e(TAG,e.getMessage());
//                }
//                Log.i(TAG,"doInBackground");
////                try {
////                    for (String photoPath : photosPath) {
////                        Log.i(TAG,"for photoPath:" +photoPath);
////
////                        File file = new File(photoPath);
////                        if (file.exists()) {
////                            Log.i(TAG, "file.exists():"+photoPath);
////                            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
////                            getParams.put("photos", fileBody);
////                        }
////                    }
////                    getParams.put("group",RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), groupId));
////                    getParams.put("content",RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), content));
////                    final Call<PostData> response = ListRestAdapter.getInstance().postingData(
////                            authorization, groupId, getParams);
////
////                    response.enqueue(new Callback<PostData>() {
////                        @Override
////                        public void onResponse(Call<PostData> call, Response<PostData> res) {
////                            responseCode = res.code();
////                            if(res.isSuccessful()){
////                                Log.i(TAG,"isSuccessful");
////                            }
////                            if(res.code() != 200){
////                                Log.i(TAG,"resCode:"+res.code());
////                                onPreExecute();
////
////                            }
////                        }
////
////                        @Override
////                        public void onFailure(Call<PostData> call, Throwable t) {
////                            Log.i(TAG,"onFailure");
////                        }
////                    });
////
////
////
////                } catch (Exception e) {
////                    e.printStackTrace();
////                    Log.i(TAG,"catch (Exception e)" );
////                }
//                return responeCode;
//            }
//
//            @Override
//            protected void onPostExecute(Integer code) {
//                super.onPostExecute(code);
//                view.progressShow(false);
//                Log.i(TAG,"onPostExecute");
//                view.writeResult(code);
//            }
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                view.progressShow(true);
//                Log.i(TAG,"onPreExecute");
//            }
//            @Override
//            protected void onProgressUpdate(Void... values) {
//                super.onProgressUpdate(values);
//            }
//        }.execute();
        view.progressShow(true);
        String authorization = "Token "+ Networking.getToken();
        String fileName="";
        Map<String, RequestBody> bodyMap = new HashMap<>();
        for(int i =0; i < photosPath.size(); i++ ){
            fileName = "photo" + i +".jpg";

            byte[] byteArray = imgResizeBitmap(photosPath.get(i));
            RequestBody body  = RequestBody.create(MediaType.parse("image/*"), byteArray, 0, byteArray.length);
            bodyMap.put("photos\"; filename=\""+fileName+"\"", body);
        }
        bodyMap.put("groupId",RequestBody.create(MediaType.parse("multipart/form-data"), groupId));
        bodyMap.put("content",RequestBody.create(MediaType.parse("multipart/form-data"), content));

        final Call<Posts> response = ListRestAdapter.getInstance().postingData(authorization, groupId, bodyMap);
        response.enqueue(new Callback<Posts>() {
            @Override
            public void onResponse(Call<Posts> call, Response<Posts> response) {
                view.writeResult(response.code());
                if(response.isSuccessful()) {
                    Log.i(TAG, "isSuccessful");
                }else{
                    Log.e(TAG,response.message());
                }
                view.progressShow(false);
            }
            @Override
            public void onFailure(Call<Posts> call, Throwable t) {
                view.progressShow(false);

                Log.i(TAG, "onFailure:"+ t.getMessage());
            }
        });

    }


    private void groupListCallHttp(){
        String authorization = "Token "+ Networking.getToken();
        Log.i(TAG, "groupListCallHttp");

        final Call<HomeData> response = ListRestAdapter.getInstance().getGroupListData(authorization);
        response.enqueue(new Callback<HomeData>() {
            @Override
            public void onResponse(Call<HomeData> call, Response<HomeData> response) {
                if(response.isSuccessful()) {
                    Log.i(TAG, "isSuccessful");
                    // 손종국 12.16 pm 7:52 원인 불명으로 아래 주석 처리
                    // view.setGroupListChanged(response.body().getResponse());

                }else{
                    Log.e(TAG,response.message());
                }
            }
            @Override
            public void onFailure(Call<HomeData> call, Throwable t) {

                Log.i(TAG, "onFailure:"+ t.getMessage());
            }
        });
    }


    @Override
    public void setView(View view) {
        this.view = view;
    }
}
