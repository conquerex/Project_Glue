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
    private final int mHandlerTime = 1200;
    SharedPreferences loginCheck;
    private final String PreferenceName ="localLoginCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        context = this;
        mHandler = new Handler();
        loginCheck = getSharedPreferences(PreferenceName, 0);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                signFlag = localLogInCheck();

                if(signFlag){
                    Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(LogoActivity.this, SignActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        }, mHandlerTime); // 2000ms
    }

    protected boolean localLogInCheck(){
        return loginCheck.getBoolean("SIGN", false);
    }


}
