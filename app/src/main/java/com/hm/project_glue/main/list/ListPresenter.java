package com.hm.project_glue.main.list;

import android.os.Handler;

import com.hm.project_glue.main.list.data.Results;

import java.util.ArrayList;

/**
 * Created by HM on 2016-11-29.
 */

public interface ListPresenter {
    void setView(ListPresenter.View view);
    ArrayList<Results> callHttp(Handler mHandler, String GroupId);
    public interface View {

    }
}
