package com.hm.project_glue.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.hm.project_glue.R;

/**
 * Created by HM on 2016-12-02.
 */

public class Networking {
    private Context context;
    private static SharedPreferences loginCheck;
    private static String BASE_URL = "";
    private static String SERVER_URL = "";


    private SharedPreferences.Editor editor;

    public Networking(Context context){
        this.context = context;
        loginCheck = context.getSharedPreferences("localLoginCheck", 0);
        BASE_URL= context.getResources().getString(R.string.BASE_URL);


    }

    public static String getToken(){        // 프리퍼런스의 현재 토큰 가져오기
        return loginCheck.getString("token","");
    }

    public static String getSERVER_URL(){
        return SERVER_URL;
    }

    public void logout(){
        editor.putString("user", "");
        editor.putString("token", "");
        editor.putBoolean("SIGN", false);
        editor.commit();
    }

}
