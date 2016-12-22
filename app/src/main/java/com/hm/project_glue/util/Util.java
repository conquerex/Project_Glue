package com.hm.project_glue.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.hm.project_glue.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by HM on 2016-12-22.
 */

public class Util {
    public static void keyboardOff(View v){
        InputMethodManager mgr = (InputMethodManager) v.getContext().getSystemService(INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
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

//        초       분   시
//        1000    60  60
            gap = (long)(gap/1000);
            long hour = gap/3600;
            gap = gap%3600;
            long min = gap/60;
            long sec = gap%60;

            if(hour >= 24){

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

        }
        return ret;
    }
}
