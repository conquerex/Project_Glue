package com.hm.project_glue.main.list;

import android.content.Context;
import android.util.Log;

import com.hm.project_glue.R;
import com.hm.project_glue.Util.Networking;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by HM on 2016-11-29.
 */

public class ListModel {

    private Context context;
    private static final String TAG = "TEST";
    private static String GET_LIST_URL = "";

    public ListModel(Context context){
        this.context = context;
        GET_LIST_URL = context.getResources().getString(R.string.BASE_URL)+
                context.getResources().getString(R.string.GET_LIST_URL);
    }

    public static String groupPostListData (String groupId, Map params) throws Exception {

        StringBuilder result = new StringBuilder();
        String dataLine;
        URL url = new URL(GET_LIST_URL+"/"+groupId+"/");
        Log.i("URL TEST : ",url.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        conn.setRequestProperty("Authorization", "Token "+Networking.getToken());
        conn.setDoInput(true);
        conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");


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
