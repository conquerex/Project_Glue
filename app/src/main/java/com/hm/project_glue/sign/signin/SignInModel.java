package com.hm.project_glue.sign.signin;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.hm.project_glue.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class SignInModel {
    private static final String TAG = "TEST";
    private static String SIGNIN_URL = "";
    Context context;


    public SignInModel(Context context){
        this.context = context;
        SIGNIN_URL = context.getResources().getString(R.string.BASE_URL)+
                context.getResources().getString(R.string.SIGNIN_URL);
    }
    public void savePreferences(String id, String token) {
        SharedPreferences loginCheck = context.getSharedPreferences("localLoginCheck", 0);
        android.content.SharedPreferences.Editor editor = loginCheck.edit();
        editor.putString("id", id);
        editor.putString("token", token);
        editor.putBoolean("SIGN", true);
        editor.commit();
    }
    public static String postData (Map params) throws Exception {

        StringBuilder result = new StringBuilder();
        String dataLine;
        URL url = new URL(SIGNIN_URL);
        Log.i("URL TEST : ",url.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

        OutputStream os = conn.getOutputStream();
        ArrayList<String> keyset = new ArrayList<>(params.keySet());

        for(String key : keyset){
            String param = key + "=" + params.get(key)+"&";
            Log.i(TAG, "POST value:"+param);
            os.write(param.getBytes());
        }
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();
        Log.i(TAG, "responseCode:" + responseCode);
        // 200
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((dataLine = br.readLine()) != null) {
                result.append(dataLine);
            }
            br.close();
        } else {
            Log.i(TAG, "" + responseCode);
        }

        Log.i(TAG, "result:" + result.toString());
        return result.toString();
    }

}
