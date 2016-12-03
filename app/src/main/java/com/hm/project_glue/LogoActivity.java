package com.hm.project_glue;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.hm.project_glue.main.MainActivity;
import com.hm.project_glue.sign.SignActivity;

public class LogoActivity extends AppCompatActivity {
    private Handler mHandler;
    private boolean signFlag = false;
    private final int mHandlerTime = 1200;
    private SharedPreferences loginCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        mHandler = new Handler();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loginCheck = getSharedPreferences("localLoginCheck", 0);
                signFlag = loginCheck.getBoolean("SIGN",false);

                if(signFlag){
                    Intent intent = new Intent(LogoActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(LogoActivity.this, SignActivity.class);
                    startActivity(intent);
                }

                finish();
            }
        }, mHandlerTime);
    }

}
