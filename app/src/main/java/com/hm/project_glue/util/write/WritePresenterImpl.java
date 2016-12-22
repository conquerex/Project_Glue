package com.hm.project_glue.util.write;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.hm.project_glue.main.home.data.HomeData;
import com.hm.project_glue.main.list.data.Posts;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.util.cache.Cache;
import com.hm.project_glue.util.http.ListRestAdapter;

import java.io.File;
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


    }



    @Override
    public void httpPosting(ArrayList<String> photosPath, String groupId, String content) {
            Log.i(TAG, "httpPosting");


        view.progressShow(true);

        Cache cache = new Cache(context);
        String authorization = "Token "+ Networking.getToken();
        String fileName="";
        Map<String, RequestBody> bodyMap = new HashMap<>();

        //캐시파일 생성
        for(int i =0; i < photosPath.size(); i++ ){
            fileName = "photo" + i +".jpg";
            try {
                cache.write(photosPath.get(i), i);
            }catch (Exception e){
                e.getMessage();
            }
            File file = new File(Environment.getExternalStorageDirectory()+"/mycachefolder/",fileName);
            if(file.exists()){
                RequestBody body  = RequestBody.create(MediaType.parse("image/*"), file);
                bodyMap.put("photos\"; filename=\""+fileName, body);
            }
        }
        bodyMap.put("group",RequestBody.create(MediaType.parse("form-data"), groupId));
        bodyMap.put("content",RequestBody.create(MediaType.parse("form-data"), content));

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
                //캐시파일 삭제
                for(int i =0; i < photosPath.size(); i++ ){
                    try {
                        File file = new File(Environment.getExternalStorageDirectory() + "/mycachefolder/", "photo" + i + ".jpg");
                        file.delete();
                    }catch (Exception e){
                        Log.e(TAG,e.getMessage());
                    }
                }
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
