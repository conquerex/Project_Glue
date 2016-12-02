package com.hm.project_glue.main.list;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HM on 2016-11-29.
 */

public class ListModel {
    private Context context;
    private SharedPreferences loginCheck;
    private SharedPreferences.Editor editor;
    public ListModel(Context context){
        this.context = context;
        loginCheck = context.getSharedPreferences("localLoginCheck", 0);
        editor = loginCheck.edit();
    }

    public String getToken(){        // 프리퍼런스의 현재 토큰 가져오기
        return loginCheck.getString("token","");
    }


}
