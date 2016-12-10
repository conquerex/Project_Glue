package com.hm.project_glue.sign.signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;
import com.hm.project_glue.R;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jongkook on 2016. 11. 30..
 *
 * ========== 참고 사항 ==========
 * 타입설정(application/json) 형식으로 전송
 * (Request Body 전달시 application/json로 서버에 전달.)
 * conn.setRequestProperty("Content-Type", "application/json");
 *
 */

public class SignUpModel {
    private static final String TAG = "SignUpModel";
    private static String SERVER_URL = "";
    Context context;

    // 2016.12.10 URL 세팅
    public SignUpModel(Context context) {
        this.context = context;
        SERVER_URL = context.getResources().getString(R.string.BASE_URL)
                + context.getResources().getString(R.string.SIGNUP_URL);
    }

    public static String postData (Map params) throws Exception{
        // 동기화 및 String보다 빠른 반응을 위해 StringBuilder 사용
        StringBuilder result = new StringBuilder();
        String dataLine;
        URL url = new URL(SERVER_URL);
        Log.i(TAG, "----------- URL ---- "+ url.toString());

        // HttpURLConnection 객체 생성
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        // 요청 방식 선택 (GET, POST)
        conn.setRequestMethod("POST");
        // Request Header값 셋팅
        // Accept-Charset : 폼을 취급하기 위해 서버가 받아 드릴 수 있는 글자 글자 엔코딩 목록
        // Accept-Charset로 euc-kr을 세팅시 IE에서 동작하지 않을 수 있음
        conn.setRequestProperty("Accept-Charset", "UTF-8");
        // content-type : request 메세지에 포함되어야 하는 정보가 있을 때, 데이터 타입 정의
        // application/x-www-form-urlencoded 방식을 선택하면, key-value 형태로 인코딩
        conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
        // Server 통신에서 입력/출력 가능한 상태로 만듬
        conn.setDoOutput(true);
        conn.setDoInput(true);
        Log.i(TAG, "----------- before OutputStream");
        // Body에 Data를 담기위해 OutputStream 객체를 생성
        OutputStream os = conn.getOutputStream();
        Log.i(TAG, "----------- After OutputStream");
        // Map의 key, value를 담아냄
        ArrayList<String> keyset = new ArrayList<>(params.keySet());
        for(String key : keyset){
            String param = key + "=" + params.get(key) + "&";
            Log.i(TAG, "----------- POST value ---- "+ param);
            // Request Body에 Data 셋팅
            os.write(param.getBytes());
        }
        // Request Body에 Data 입력
        os.flush();
        // OutputStream 종료
        os.close();

        // 실제 서버로 Request 요청 하는 부분 (응답 코드를 받는다. 200 혹은 201 성공, 나머지 에러)
        int responseCode = conn.getResponseCode();
        Log.i(TAG, "----------- if ---- "+ responseCode);

        if(responseCode == HttpURLConnection.HTTP_OK
                || responseCode == HttpURLConnection.HTTP_CREATED){

            // Body에 Data를 담기위해 InputStream 객체를 생성
            InputStream is = conn.getInputStream();

            // 스트림을 직접 읽으면 느리고 비효율적
            // 버퍼(BufferedReader)를 지원하는 보조 스트림 객체로 감싸서 사용
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            // 입력받은 값이 null이 아니면 result에 담음
            while ((dataLine = br.readLine()) != null){
                result.append(dataLine);
            }
        } else {
            Log.i(TAG, "----------- responseCode ---- "+ responseCode);
        }

        return result.toString();
    }
}