package com.hm.project_glue.main.timeline;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hm.project_glue.R;
import com.hm.project_glue.main.timeline.data.TimelineData;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.util.http.ListRestAdapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
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
    public static String timeChange(Context context, String gettime){
        String after = gettime.replace('T', '/');
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss", Locale.KOREA);
        String ret = "";
        try{
            Date date = dateFormat.parse(after);
            Calendar c = Calendar.getInstance();
            long now = c.getTimeInMillis();
            long dateM = date.getTime();
            long gap = now - dateM;
            Log.e(TAG,"Now  : "+c.getTime().toString());
            Log.e(TAG,"GETtime  : "+date.getTime());
//        초       분   시
//        1000    60  60
            gap = (long)(gap/1000);
            long hour = gap/3600;
            gap = gap%3600;
            long min = gap/60;
            long sec = gap%60;

            if(hour >= 24){
                Log.i(TAG,"hour:"+hour);
                int gapDay = (int) (hour/24);
                if(gapDay >= 7){
                    if(gapDay > 31) {
                        if(gapDay > 365){
                            ret = gapDay / 365 + context.getResources().getString(R.string.timeAgoYear);
                        }else{
                            ret = gapDay / 30 + context.getResources().getString(R.string.timeAgoMonth);
                        }
                    }else{
                        ret = gapDay / 7 + context.getResources().getString(R.string.timeAgoWeek);
                    }

                }else{
                    ret = gapDay+context.getResources().getString(R.string.timeAgoDay);
                }
//                ret = new SimpleDateFormat("HH:mm").format(date);
            }
            else if(hour >= 1){
                ret = hour+context.getResources().getString(R.string.timeAgoHour);

            }
            else if(min >= 1){
                ret = min+context.getResources().getString(R.string.timeAgoMin);
            }
            else if(sec >= 1){
                ret = sec+context.getResources().getString(R.string.timeAgoSec);
            }
        }catch (Exception e){
            Log.i(TAG,e.getMessage());
        }
        return ret;
    }
}
