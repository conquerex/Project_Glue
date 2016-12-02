package com.hm.project_glue.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HM on 2016-12-02.
 */

public class Networking {
    private Context context;
    private static SharedPreferences loginCheck;
    private SharedPreferences.Editor editor;

    public Networking(Context context){
        this.context = context;
        loginCheck = context.getSharedPreferences("localLoginCheck", 0);
        editor = loginCheck.edit();
    }

    public static String getToken(){        // 프리퍼런스의 현재 토큰 가져오기
        return loginCheck.getString("token","");
    }
    public void logout(){

        editor.putString("user", "");
        editor.putString("token", "");
        editor.putBoolean("SIGN", false);
        editor.commit();
    }

}
