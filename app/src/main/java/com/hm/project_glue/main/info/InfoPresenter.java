package com.hm.project_glue.main.info;

import android.graphics.Bitmap;

import com.hm.project_glue.main.info.data.InfoData;

/**
 * Created by HM on 2016-11-29.
 */

public interface InfoPresenter {

    void setView(InfoPresenter.View view);
    void infoFormCheck(String phone, String name, String password1, String password2, String email);
    Bitmap imgReSizing(String path);
    interface View{
        void setInfo(InfoData infoData);
        void toast(String msg);
    }
}
