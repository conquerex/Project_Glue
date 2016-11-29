package com.hm.project_glue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.hm.project_glue.main.MainActivity;
import com.hm.project_glue.sign.SignActivity;

public class LogoActivity extends AppCompatActivity {
    Handler mHandler;
    Context context;
    private boolean signFlag = false;
    private boolean firstRunFlag = false;
    private final int mHandlerTime = 2000;
    SharedPreferences loginCheck;
    SharedPreferences.Editor editor;
    boolean test = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        context = this;
        mHandler = new Handler();
        loginCheck = getSharedPreferences("localLoginCheck", 0);
        editor = loginCheck.edit();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                firstRunFlag = firstRunCheck();
                signFlag = localLogInCheck();

//                if (firstRunFlag||!signFlag){ // 최초실행 or 로그인 기록
                if(test){
                    Intent intent = new Intent(LogoActivity.this, SignActivity.class);
                    startActivity(intent);
                }else { // !최초실행 or !로그인기록
                    Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                    startActivity(intent);
                }
//                Toast.makeText(context, "Logo Test", Toast.LENGTH_SHORT).show();

                finish();
            }
        }, mHandlerTime); // 2000ms

    }
    protected boolean firstRunCheck(){
        return loginCheck.getBoolean("firstRun",true);
    }
    protected boolean localLogInCheck(){
        return loginCheck.getBoolean("SIGN", false);
    }


}
