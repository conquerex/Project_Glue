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
    private final String SERVER_URL = "http://dummy-dev.ap-northeast-2.elasticbeanstalk.com/group/";
    // 서버 완성시 아래 적용
    // private final String SERVER_URL = R.string.BASE_URL + R.string.SIGNUP_URL + "";

    public void signUp(String id, String pw, String pwre,
                       String email, String name, String phone, Context context) {
        HashMap hashMap = new HashMap();

        hashMap.put("id", id);
        hashMap.put("pw", pw);
        hashMap.put("pwre", pwre);
        hashMap.put("phone", phone);
        hashMap.put("name", name);
        hashMap.put("email", email);
        // AsyncTask클래스는 항상 Subclassing 해서 사용 해야 함.
        // UI 처리 및 Background 작업 등 을 하나의 클래스에서 작업 할 수 있게 지원
        // 파라미터 타입은 작업 실행 시에 송신 : Map (doInBackground 파라미터 타입이, execute 메소드 인자값)
        // doInBackground 작업 시 진행 단위의 타입 : Void (onProgressUpdate 파라미터 타입)
        // doInBackground 리턴값 : String (onPostExecute 파라미터 타입)
        // 인자를 사용하지 않은 경우 Void Type 으로 지정
        new AsyncTask<Map, Void, String>(){
            ProgressDialog progress;
            @Override
            // doInBackground : Background 작업을 진행
            // doInBackground의 매개값 : Map
            protected String doInBackground(Map... params) {
                String result = "";
                Log.i(TAG, "----------- doInBackground ");
                try {
                    // SERVER_URL에서 보낸 값을 받음
                    result = postData(SERVER_URL, params[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return result;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress = new ProgressDialog(context);
                progress.setMessage("Loging....");
                progress.setProgressStyle((ProgressDialog.STYLE_SPINNER));
                progress.setCancelable(false);
                progress.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i(TAG, "----------- onPostExecute ---- " + s);

                try {
                    Log.i(TAG, "----------- 로그인 성공");
                    progress.dismiss();
                    Toast.makeText(context, "회원가입 완료", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.i(TAG, "----------- 로그인 실패");
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle(R.string.loginfailtitle); // "로그인실패"
                    alert.setMessage(R.string.loginfailmessage); // "아이디와 ..."
                    alert.setNegativeButton(R.string.ok, null); // "확인"
                    alert.show();
                    e.printStackTrace();
                }
            }
        }.execute(hashMap);
    }

    public static String postData (String webURL, Map params) throws Exception{
        // 동기화 및 String보다 빠른 반응을 위해 StringBuilder 사용
        StringBuilder result = new StringBuilder();
        String dataLine;
        URL url = new URL(webURL);
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

        // Body에 Data를 담기위해 InputStream 객체를 생성
        InputStream is = conn.getInputStream();
        // 실제 서버로 Request 요청 하는 부분 (응답 코드를 받는다. 200 혹은 201 성공, 나머지 에러)
        int responseCode = conn.getResponseCode();

        if(responseCode == HttpURLConnection.HTTP_OK
                || responseCode == HttpURLConnection.HTTP_CREATED){
            // 스트림을 직접 읽으면 느리고 비효율적
            // 버퍼(BufferedReader)를 지원하는 보조 스트림 객체로 감싸서 사용
            Log.i(TAG, "----------- if ---- "+ responseCode);
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