package com.hm.project_glue.main.home;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hm.project_glue.main.home.data.HomeData;
import com.hm.project_glue.util.Networking;

import retrofit2.Call;

/**
 * Created by HM on 2016-11-29.
 */

public class HomePresenterImpl implements HomePresenter {
    private static final String TAG = "HomePresenterImpl";
    private HomeFragment fragment;
    private HomeModel model;
    private HomePresenter.View view;
    Context context;


    // 2016.12.06
    public HomePresenterImpl(HomeFragment fragment) {
        this.fragment = fragment;
        model = new HomeModel(fragment.getContext());
        context = fragment.getActivity();
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    // 2016.12.06
    @Override
    public void callHttp() {
        new AsyncTask<String, Void, HomeData>(){
            @Override
            protected HomeData doInBackground(String... params) {
                String token = "Token "+ Networking.getToken();
                HomeData res=null;
                try{
                    final Call<HomeData> response = HomeRestAdapter.getInstance().getData(token);
                    res = response.execute().body();

                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
                return res;
            }
            @Override
            protected void onPreExecute() {
                Log.i(TAG, "----------- in onPreExecute");
                super.onPreExecute();
                //TODO  메인쓰레드 프로그래스 바 보여주기
            }
            @Override
            protected void onPostExecute(HomeData res) {
                super.onPostExecute(res);
                view.dataChanged(res);
            }
        }.execute();
    }
}
