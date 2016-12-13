package com.hm.project_glue.util.write;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hm.project_glue.main.list.data.PostData;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.util.http.ListRestAdapter;
import com.hm.project_glue.util.write.data.GroupListData;

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
        groupListCallHttp();

    }

//    @Path("groupId") String groupId,
//    @Field("content") String content,
//    @Field("group") String group,
//    @PartMap() Map<String, RequestBody> partMap
    @Override
    public void httpPosting(ArrayList<String> photosPath, String groupId, String content){
            Log.i(TAG, "httpPosting");
        new AsyncTask<String, Void, String>(){
            ProgressDialog progress = new ProgressDialog(context);
            String MULTIPART_FORM_DATA = "multipart/form-data";
            String authorization = "Token "+Networking.getToken();
            int responseCode = 0;
            @Override
            protected String doInBackground(String... params) {
                Map<String, RequestBody> files  = new HashMap<>();
                Map<String, RequestBody> getParams = new HashMap<>();
                Log.i(TAG,"doInBackground");
                try {
                    for (String photoPath : photosPath) {
                        Log.i(TAG,"for photoPath:" +photoPath);
                        File file = new File(photoPath);
                        if (file.exists()) {
                            Log.i(TAG, "file.exists():"+photoPath);
                            files.put("photos",RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file));
                        }
                    }
                    Log.i(TAG,"doInBackground try");
                    getParams.put("group",RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), groupId));
                    getParams.put("content",RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), content));
                    final Call<PostData> response = ListRestAdapter.getInstance().postingData(
                            authorization, groupId, getParams, files);

                    response.enqueue(new Callback<PostData>() {
                        @Override
                        public void onResponse(Call<PostData> call, Response<PostData> res) {
                            responseCode = res.code();
                            if(res.isSuccessful()){
                                Log.i(TAG,"isSuccessful");
                            }
                            if(res.code() != 200){
                                Log.i(TAG,"resCode:"+res.code());
                                onPreExecute();

                            }
                        }

                        @Override
                        public void onFailure(Call<PostData> call, Throwable t) {
                            Log.i(TAG,"onFailure");
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG,"catch (Exception e)" );
                }
                return "";
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                progress.setMessage("Upload....");
                progress.setProgressStyle((ProgressDialog.STYLE_SPINNER));
                progress.setCancelable(false);
                progress.show();
                Log.i(TAG,"onPostExecute");
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress.dismiss();
                view.writeResult(responseCode);
                Log.i(TAG,"onPreExecute");
            }
        }.execute();

    }


    private void groupListCallHttp(){
        String authorization = "Token "+ Networking.getToken();
        Log.i(TAG, "groupListCallHttp");
        final Call<GroupListData> response = ListRestAdapter.getInstance().getGroupListData(authorization);
        response.enqueue(new Callback<GroupListData>() {
            @Override
            public void onResponse(Call<GroupListData> call, Response<GroupListData> response) {
                if(response.isSuccessful()) {
                    Log.i(TAG, "isSuccessful");
                    view.setGroupListChanged(response.body().getResults());
                }else{
                    Log.e(TAG,response.message());
                }
            }
            @Override
            public void onFailure(Call<GroupListData> call, Throwable t) {
                Log.i(TAG, "onFailure:"+ t.getMessage());
            }
        });
    }


    @Override
    public void setView(View view) {
        this.view = view;
    }
}
