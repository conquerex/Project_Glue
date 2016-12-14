package com.hm.project_glue.util.write;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hm.project_glue.main.home.data.HomeData;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.util.http.ListRestAdapter;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
        groupListCallHttp();

    }

//    @Path("groupId") String groupId,
//    @Field("content") String content,
//    @Field("group") String group,
//    @PartMap() Map<String, RequestBody> partMap
    @Override
    public void httpPosting(ArrayList<String> photosPath, String groupId, String content){
            Log.i(TAG, "httpPosting");
        new AsyncTask<String, Void, Integer>(){
            String boundary="----------";
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            int maxBufferSize = 1 * 1024 * 1024;
            int bufferSize;
            public static final int MAX_READ_TIME = 10000;
            public static final int MAX_CONNECT_TIME = 15000;
            int responseCode = 0;
            @Override
            protected Integer doInBackground(String... params) {
                int responeCode = 0;

                try {
                    URL connectUrl = new URL(Networking.getBASE_URL() + "/posts/post_list/" + groupId + "/");
                    HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type","multipart/form-data; boundary="+boundary);
                    conn.setRequestProperty("Authorization", "Token "+Networking.getToken());
                    conn.setRequestProperty("cache-control", "no-cache");
                    conn.connect();

                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes("\r\n--" + boundary + "\r\n");
                    dos.writeBytes("Content-Disposition: form-data; name=\"content\"\r\n\r\n" + content);
                    dos.writeBytes("\r\n--" + boundary + "\r\n");
                    dos.writeBytes("Content-Disposition: form-data; name=\"group\"\r\n\r\n" + groupId);

                    //photo가 있으면
                    for(String photopath : photosPath){
                        Log.i(TAG,"for / photopath:"+photopath);
                        dos = new DataOutputStream(conn.getOutputStream());
                        File file = new File(photopath);
                        if(file.exists()){
                            Log.i(TAG,"file.exists()");
                        }
                        FileInputStream mFileInputStream = new FileInputStream(file);

                        dos.writeBytes("\r\n--" + boundary + "\r\n");
                        dos.writeBytes("Content-Disposition: form-data; name=\"photos\";filename=\"" + photopath + "\"" + lineEnd);
                        dos.writeBytes("Content-Type: application/octet-stream" + lineEnd);
                        dos.writeBytes(lineEnd);

                        int bytesAvailable = mFileInputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        byte[] buffer = new byte[bufferSize];
                        int bytesRead = mFileInputStream.read(buffer, 0, bufferSize);
                        while (bytesRead > 0)
                        {
                            Log.i(TAG, "bytesRead: "+bytesRead);
                            dos.write(buffer, 0, bufferSize);
                            bytesAvailable = mFileInputStream.available();
                            bufferSize = Math.min(bytesAvailable, maxBufferSize);
                            bytesRead = mFileInputStream.read(buffer, 0, bufferSize);

                        }
                        mFileInputStream.close();
                    }

                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                    dos.flush();
                    if(conn.getResponseCode() == 200 ||conn.getResponseCode() == 201 ){
                        Log.i(TAG,"conn.getResponseCode(): "+conn.getResponseCode() );
                        responeCode = conn.getResponseCode() ;
                    }else{
                        Log.i(TAG,"conn.getResponseCode(): "+conn.getResponseCode() );
                        responeCode = conn.getResponseCode() ;
                    }
                }catch(Exception e){
                    Log.e(TAG,e.getMessage());
                }
                Log.i(TAG,"doInBackground");
//                try {
//                    for (String photoPath : photosPath) {
//                        Log.i(TAG,"for photoPath:" +photoPath);
//
//                        File file = new File(photoPath);
//                        if (file.exists()) {
//                            Log.i(TAG, "file.exists():"+photoPath);
//                            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
//                            getParams.put("photos", fileBody);
//                        }
//                    }
//                    getParams.put("group",RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), groupId));
//                    getParams.put("content",RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), content));
//                    final Call<PostData> response = ListRestAdapter.getInstance().postingData(
//                            authorization, groupId, getParams);
//
//                    response.enqueue(new Callback<PostData>() {
//                        @Override
//                        public void onResponse(Call<PostData> call, Response<PostData> res) {
//                            responseCode = res.code();
//                            if(res.isSuccessful()){
//                                Log.i(TAG,"isSuccessful");
//                            }
//                            if(res.code() != 200){
//                                Log.i(TAG,"resCode:"+res.code());
//                                onPreExecute();
//
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<PostData> call, Throwable t) {
//                            Log.i(TAG,"onFailure");
//                        }
//                    });
//
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.i(TAG,"catch (Exception e)" );
//                }
                return responeCode;
            }

            @Override
            protected void onPostExecute(Integer code) {
                super.onPostExecute(code);
                view.progressShow(false);
                Log.i(TAG,"onPostExecute");
                view.writeResult(responseCode);
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                view.progressShow(true);
                Log.i(TAG,"onPreExecute");
            }
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }.execute();

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
                    view.setGroupListChanged(response.body().getResponse());

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
