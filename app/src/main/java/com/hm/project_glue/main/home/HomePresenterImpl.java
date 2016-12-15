package com.hm.project_glue.main.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hm.project_glue.util.Networking;
import com.hm.project_glue.main.home.data.HomeData;
import java.util.HashMap;
import java.util.Map;

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
    HomeData homeData;

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
    public void callHttp(HomeData data) {
        this.homeData = data;
        Log.i(TAG, "----------- callHttp");
        ProgressDialog progress = new ProgressDialog(context);
        Log.i(TAG, "----------- after progress");
        new AsyncTask<String, Void, HomeData>(){
            @Override
            protected HomeData doInBackground(String... params) {
                String token = "Token "+ Networking.getToken();
                Log.i(TAG, "----------- token ---- "+ token);
                Map<String, String> queryMap = new HashMap<>();

                try{
                    final Call<HomeData> response = HomeRestAdapter.getInstance().getData(token, queryMap);
                    homeData = response.execute().body();
                    Log.i(TAG, "----------- response.execute().body() --- "+homeData);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return homeData;
            }

            @Override
            protected void onPreExecute() {
                Log.i(TAG, "----------- in onPreExecute");
                super.onPreExecute();
                //TODO  메인쓰레드 프로그래스 바 보여주기
                progress.setMessage("Loging....");
                progress.setProgressStyle((ProgressDialog.STYLE_SPINNER));
                progress.setCancelable(false);
                progress.show();
                Log.i(TAG, "----------- end of onPreExecute");
            }

            @Override
            protected void onPostExecute(HomeData res) {
                super.onPostExecute(res);
                Log.i(TAG, "----------- onPostExecute ------- " + res.getHomeResponses());
                progress.dismiss();
                view.dataChanged(res);
            }

        }.execute();
    }
}
