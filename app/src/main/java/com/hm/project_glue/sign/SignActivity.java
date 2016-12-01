package com.hm.project_glue.sign;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.hm.project_glue.R;
import com.hm.project_glue.main.MainActivity;
import com.hm.project_glue.sign.signin.SignInFragment;
import com.hm.project_glue.sign.signup.SignUpFragment;

import org.json.JSONObject;

import java.util.Arrays;


public class SignActivity extends AppCompatActivity {

    SharedPreferences loginCheck;
    SharedPreferences.Editor editor;
    SignInFragment signInFragment;
    SignUpFragment signUpFragment;
    private CallbackManager callbackManager;
    private final String PreferenceName ="localLoginCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        signInFragment = SignInFragment.newInstance();
        signUpFragment = SignUpFragment.newInstance();
        loginCheck = getSharedPreferences(PreferenceName, 0);
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
        transaction.add(R.id.fragment,signUpFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        keyBoardOff();
        Log.i("test","goToSignUpFragment");
    }

    public void keyBoardOff() {  // 키보드 내리기
        View view = getCurrentFocus();
        InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    // 로그인 토큰 프리퍼런스 저장
    public void savePreferences(String user, String token){
        editor.putString("user", user);
        editor.putString("token", token);
        editor.putBoolean("SIGN", true);
        editor.commit();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    public void facebookLoginOnClick(View v){
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(SignActivity.this,
                Arrays.asList("public_profile", "email"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(final LoginResult result) {

                GraphRequest request;
                request = GraphRequest.newMeRequest(result.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject user, GraphResponse response) {
                        if (response.getError() != null) {
                            Log.i("response.getError", "user: " + user.toString());
                            Log.i("response.getError","AccessToken: " + result.getAccessToken().getToken());
                        } else {
                            Log.i("TAG", "user: " + user.toString());
                            Log.i("TAG", "AccessToken: " + result.getAccessToken().getToken());

                            savePreferences(user.toString(),result.getAccessToken().getToken());
                            setResult(RESULT_OK);

                            Intent i = new Intent(SignActivity.this, MainActivity.class);
                            startActivity(i);
                            Log.i("TAG", "startActivity");
//                            finish();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e("test", "Error: " + error);
                //finish();
            }

            @Override
            public void onCancel() {
                //finish();
            }
        });
    }


}
