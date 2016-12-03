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
    SignInFragment signInFragment;
    SignUpFragment signUpFragment;
    private CallbackManager callbackManager;
    SharedPreferences loginCheck;
    SharedPreferences.Editor editor;
    private final String preferenceName ="localLoginCheck";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        signInFragment = SignInFragment.newInstance();
        signUpFragment = SignUpFragment.newInstance();
        loginCheck = getSharedPreferences(preferenceName,0);
        editor = loginCheck.edit();
        setOne();
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
    }

    public void keyBoardOff() {  // 키보드 내리기
        View view = getCurrentFocus();
        InputMethodManager mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    // 로그인 토큰 프리퍼런스 저장
    public void savePreferences(String user, String token){
        editor.putString("user", user);
        editor.putString("token", token);
        editor.putBoolean("SIGN", true);
        editor.commit();
    }
    public void moveActivity(){
        Intent i = new Intent(SignActivity.this, MainActivity.class);
        startActivity(i);
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

                        } else {
                            Log.i("TAG", "user: " + user.toString());
                            Log.i("TAG", "AccessToken: " + result.getAccessToken().getToken());

                            //TODO 페북 토큰을 서버에 전달 후 서버에서 다시 발급한 토큰을 프리퍼런스에 저장해야함.
                            savePreferences(user.toString(),result.getAccessToken().getToken());
                            setResult(RESULT_OK);

                            Intent i = new Intent(SignActivity.this, MainActivity.class);
                            startActivity(i);


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

            }

            @Override
            public void onCancel() {

            }
        });
    }


}
