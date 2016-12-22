package com.hm.project_glue.main.timeline;

import android.content.Context;
import android.os.AsyncTask;

import com.hm.project_glue.main.timeline.data.TimelineData;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.util.http.ListRestAdapter;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by HM on 2016-12-18.
 */

public class TimelinePresentImpl implements TimelinePresenter{
    private TimelineFragment fragment;
    private TimelinePresenter.View view;
    private static final String TAG = "TEST";
    private Context context;
    public TimelinePresentImpl(TimelineFragment fragment) {
        this.fragment = fragment;
        context = fragment.getActivity();

    }

    @Override
    public void setView(TimelinePresenter.View view) {
        this.view = view;
    }

    @Override
    public void callHttp(String page , boolean refresh) {
        TimelineFragment.loadingFlag=true;
        String authorization = "Token " + Networking.getToken();
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("page", page);
        new AsyncTask<String, Void, String>() {
            TimelineData timelineData = null;
            protected String doInBackground(String... params) {
                try {
                    final Call<TimelineData> response = ListRestAdapter.getInstance().getTimeLinetData(authorization, queryMap);
                    timelineData = response.execute().body();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }
            protected void onPreExecute() {
                super.onPreExecute();
                //TODO  메인쓰레드 프로그래스 바 보여주기
                if (refresh) view.setProgress(1);
                else view.setProgress(3);
            }
            protected void onPostExecute(String  str) {
                super.onPostExecute(str);
                if (refresh) view.setProgress(2);
                else view.setProgress(4);
                if (timelineData != null) view.dataChanged(timelineData);
            }
        }.execute();
    }

}
