package com.hm.project_glue.util;

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
    private static int responseCode = 0 ;
    private static String selectedGroupId;
    private static String selectedGroupName;

    private SharedPreferences.Editor editor;

    public Networking(Context context){
        this.context = context;
        // getSharedPreferences : 해당 프로세스(어플리케이션)내에 File 형태로 Data를 저장
        // 로그인한뒤 받은 Response에서 쿠키정보를 안드로이드의 SharedPreferences에 저장
        loginCheck = context.getSharedPreferences("localLoginCheck", 0);
        BASE_URL= context.getResources().getString(R.string.BASE_URL);
        editor = loginCheck.edit();
    }

    public static String getToken(){        // 프리퍼런스의 현재 토큰 가져오기
        return loginCheck.getString("token","");
    }

    public static void setGroupId(String groupId){
        selectedGroupId = groupId;
    }
    public static void setGroupName(String groupName){
        selectedGroupId = groupName;
    }
    public static String getGroupName(){
        return selectedGroupName;
    }
    public static String getGroupId(){
        return selectedGroupId;
    }
    public static String getBASE_URL(){
        return BASE_URL;
    }
    public static void  setResponseCode(int code){
        responseCode = code;
    }
    public static int  getResponseCode(){
        return responseCode;
    }
    public void logout(){
        editor.putString("user", "");
        editor.putString("token", "");
        editor.putBoolean("SIGN", false);
        editor.commit();
    }

}
