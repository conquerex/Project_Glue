package com.hm.project_glue.util.addGroup;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import java.util.Map;

/**
 * Created by jongkook on 2016. 12. 19..
 */

public class AddGroupPresenterImpl implements AddGroupPresenter {
    private static final String TAG = "AddGroupPresenterImpl";
    AddGroupPresenter.View view;

    @Override
    public void setView(View view) {
        new AsyncTask<String, Void, Integer>(){
            ProgressDialog progress;
            @Override
            protected Integer doInBackground(String... params) {
                String result = "";
                Log.i(TAG, "----------- doInBackground ");
                try {
                    // SERVER_URL에서 보낸 값을 받음

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Integer code) {
                super.onPostExecute(code);
                view.progressAddGroupShow(false);
                Log.i(TAG,"onPostExecute");
                view.addGroupResult(code);
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                view.progressAddGroupShow(false);
                Log.i(TAG,"onPreExecute");
            }
        }.execute();
    }

    @Override
    public void addHttp() {

    }
}
