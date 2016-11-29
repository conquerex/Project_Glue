package com.hm.project_glue.sign.signin;


import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class SignInModel {
    private static final String TAG = "ResponseCode : ";
    private final static String SERVER_URL = "http://dummy-dev.ap-northeast-2.elasticbeanstalk.com/group/";

    public void signIn(String id, String pw)  {
        HashMap userInfoMap =   new HashMap();


        userInfoMap.put("id", id);
        userInfoMap.put("pw", pw);

        new AsyncTask<Map, Void, String>(){

            @Override
            protected String doInBackground(Map... params) {
                String result = "";

                try {
                    result = postData(SERVER_URL, params[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
//                StringBuffer sb=  new StringBuffer();
//                List<HttpCookie> cookies =  Remote.cookieManager.getCookieStore().getCookies();
//                for( HttpCookie cookie : cookies){
//                    sb.append(cookie.getName()+"="+cookie.getValue()+"\n");
//                    editor.putString(cookie.getName(),cookie.getValue());
//                }
//                editor.commit();
//                textResult.setText(sb.toString());
//
//                progress.dismiss();
            }
        }.execute(userInfoMap);
    }
    public static String postData (String webURL, Map params) throws Exception {

        StringBuilder result = new StringBuilder();
        String dataLine;
        URL url = new URL(webURL);
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
            Log.i("POST value : ", param);
            os.write(param.getBytes());
        }
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();
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


        return result.toString();
    }

}
