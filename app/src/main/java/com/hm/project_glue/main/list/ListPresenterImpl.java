package com.hm.project_glue.main.list;

import android.content.Context;
import android.os.AsyncTask;

import com.hm.project_glue.main.list.data.PostData;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.util.http.ListRestAdapter;

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


    public ListPresenterImpl(ListFragment fragment) {
        this.fragment=fragment;
        listInModel = new ListModel(fragment.getContext());
        context = fragment.getActivity();



    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void callHttp(String GroupId){

         new AsyncTask<String, Void, PostData>(){
             PostData postData;
            @Override
            protected PostData doInBackground(String... params) {

                String authorization = "Token "+ Networking.getToken();
                Map<String, String> queryMap = new HashMap<>();
                queryMap.put("page", "1");
                try {
                    final Call<PostData> response = ListRestAdapter.getInstance().getListData(authorization, GroupId, queryMap);
                    postData = response.execute().body();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return postData;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //TODO  메인쓰레드 프로그래스 바 보여주기
            }
            @Override
            protected void onPostExecute(PostData postData) {
                super.onPostExecute(postData);
                view.dataChanged(postData);
            }
        }.execute();



    }





}
