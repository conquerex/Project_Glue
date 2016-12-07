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
        // getSharedPreferences : 해당 프로세스(어플리케이션)내에 File 형태로 Data를 저장
        // 로그인한뒤 받은 Response에서 쿠키정보를 안드로이드의 SharedPreferences에 저장
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
