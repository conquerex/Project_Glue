package com.hm.project_glue.sign;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hm.project_glue.R;
import com.hm.project_glue.sign.signin.SignInFragment;
import com.hm.project_glue.sign.signup.SignUpFragment;


public class SignActivity extends AppCompatActivity {

    SharedPreferences loginCheck;
    SharedPreferences.Editor editor;
    SignInFragment signInFragment;
    SignUpFragment signUpFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        signInFragment = SignInFragment.newInstance();
        signUpFragment = SignUpFragment.newInstance();
        loginCheck = getSharedPreferences("localLoginCheck", 0);
        editor = loginCheck.edit();


        init();
        setOne();
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
//        localID = etId.getText().toString();
//        localPW = etPasswd.getText().toString();
//        editor.putString("ID", localID);
//        editor.putString("PW", localPW);
        editor.putBoolean("SIGN", true);
        editor.commit();
        Log.i("setAutoLogin","commit");
    }

    public void setOne(){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment,signInFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    public void goToSignUpFragment(){

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment,signUpFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        Log.i("test","goToSignUpFragment");
    }


}
