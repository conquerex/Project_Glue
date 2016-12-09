package com.hm.project_glue.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

/**
 * Created by HM on 2016-12-06.
 */

public class LooperHandler extends HandlerThread {
    public static final int SHOW_PROGRESS = 1;
    public static final int HIDE_PROGRESS = 2;
    Handler looperHandler;
    Context context;
    ProgressDialog progress;
    public LooperHandler(Context context ,String name) {
        super(name);
        this.context = context;
        progress = new ProgressDialog(context);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setTitle("Progress Bar Title");
        progress.setMessage("Message");
        progress.setCancelable(false);
    }
    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        looperHandler = new Handler(){
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case SHOW_PROGRESS:
                        showProgress();
                        break;
                    case HIDE_PROGRESS:
                        hideProgress();
                        break;
                }
            }
        };
    }
    private void showProgress(){
        Log.i("LooperHandler","진행상태바 보여주기");
        progress.show();
    }
    private void hideProgress(){
        Log.i("LooperHandler","진행상태바 해제하기");
        progress.dismiss();
    }
}
