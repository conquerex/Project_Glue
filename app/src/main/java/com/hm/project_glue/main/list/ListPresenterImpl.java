package com.hm.project_glue.main.list;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hm.project_glue.main.list.data.PostData;
import com.hm.project_glue.main.list.data.Results;
import com.hm.project_glue.util.Networking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;


/**
 * Created by HM on 2016-11-29.
 */
//conn.setRequestProperty("Value", "test");
public class ListPresenterImpl implements ListPresenter {
    private ListFragment fragment;
    private ListModel listInModel;
    private ListPresenter.View view;
    private static final String TAG = "TEST";
    Context context;
    ArrayList<Results> post;

    public ListPresenterImpl(ListFragment fragment) {
        this.fragment=fragment;
        listInModel = new ListModel(fragment.getContext());
        context = fragment.getActivity();
        post = new ArrayList<>();
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public  void callHttp(ArrayList<Results> post, String GroupId){
        ProgressDialog progress = new ProgressDialog(context);
         new AsyncTask<String, Void, ArrayList<Results>>(){
            @Override
            protected ArrayList<Results> doInBackground(String... params) {
                ArrayList<Results> results = null;
                String authorization = "Token "+ Networking.getToken();
                Map<String, String> queryMap = new HashMap<>();
                queryMap.put("page", "2");
                try {
                    final Call<PostData> postData = ListRestAdapter.getInstance().getListData(authorization, GroupId, queryMap);
                    Log.i(TAG, "final Call<PostData> ");
                    PostData body = postData.execute().body();

                    for (Results result : body.getResults()) { // 게시물 단위
                        Log.i(TAG, "Results result : item.getResults()");
                        Results data = new Results();
                        data.setContent(result.getContent());
                        data.setUploaded_user(result.getUploaded_user());
                        data.setLikes_count(result.getLikes_count());
                        data.setGroup(result.getGroup());
                        data.setPhotos(result.getPhotos());

                        Log.i(TAG, "post.add");
                        post.add(data);
                    }

                    Log.i(TAG,"results");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return results;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //TODO  메인쓰레드 프로그래스 바 보여주기

                progress.setMessage("Loging....");
                progress.setProgressStyle((ProgressDialog.STYLE_SPINNER));
                progress.setCancelable(false);
                progress.show();
            }
            @Override
            protected void onPostExecute(ArrayList<Results> listResults) {
                super.onPostExecute(listResults);
                progress.dismiss();
                view.dataChanged(post);

            }
        }.execute();

        // Callback ( 비동기 방식)
//        postData.enqueue(new Callback<PostData>() {
//            @Override
//            protected Object clone() throws CloneNotSupportedException {
//                return super.clone();
//
//
//
//            }
//
//            @Override
//            public void onResponse(Call<PostData> call, Response<PostData> response) {
//
//                if(response.isSuccessful()){
//                    PostData body = response.body();
//                    List<Results> listResults = body.getResults();
//
//                    for (Results result : listResults) {
//                        Results data = new Results();
//                        data.setContent(result.getContent());
//                        data.setUploaded_user(result.getUploaded_user());
//                        data.setLikes_count(result.getLikes_count());
//                        data.setGroup(result.getGroup());
//                        data.setPhotos(result.getPhotos());
//
//
//
//                        for (Photo photo_URL : result.getPhotos().getPhotos()) {
//                            Photo photo = new Photo();
//                            photo.setFull_size(photo_URL.getFull_size());
//                            Log.i(TAG, "setFull_size:"+photo.getFull_size());
//                            data.getPhotos().getPhotos().add(photo);
//                        }
//
//                        post.add(data);
//                    }
//
//                    Log.i(TAG, "onResponse"+response.body().toString());
//                    view.dataChanged(post);
//
//
//
//                }
//                else{
//                    Log.e("ERROR : ",response.message());
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<PostData> call, Throwable t) {
//
//            }
//
//        });
//        Log.i(TAG,"callHttp : "+post.toString());

    }





}
