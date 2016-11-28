package com.hm.project_glue.sign;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hm.project_glue.R;


public class SignActivity extends AppCompatActivity {
    Button btnSignIn, btnSignUp, btnFindId;
    EditText etId, etPasswd;
    SharedPreferences loginCheck;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        loginCheck = getSharedPreferences("localLoginCheck", 0);
        editor = loginCheck.edit();


        etId = (EditText) findViewById(R.id.etId);
        etPasswd = (EditText) findViewById(R.id.etPasswd);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnFindId = (Button) findViewById(R.id.btnFindId);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAutoLogin();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        init();

    }
    private void init(){
        boolean firstRun;
        firstRun = loginCheck.getBoolean("firstRun", true);
        if(firstRun) {
            editor.putBoolean("firstRun", false);
            editor.commit();
        }

    }
    private void setAutoLogin() {
        String localID, localPW;
        localID = etId.getText().toString();
        localPW = etPasswd.getText().toString();
        editor.putString("ID", localID);
        editor.putString("PW", localPW);
        editor.putBoolean("SIGN", true);
        editor.commit();
        Log.i("setAutoLogin","commit");
    }
 }
