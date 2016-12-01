package com.hm.project_glue.sign.signup;

import android.util.Log;

/**
 * Created by jongkook on 2016. 11. 30..
 */

public class SignUpPresenterImpl implements SignUpPresenter {
    private static final String TAG = "SignUpPresenterImpl";

    // 모든 기능은 MVP를 따르며 현재 SignUpPresenterImpl가 Presenter 역할을 한다
    // SignUp View
    private SignUpFragment signUpFragment;
    // SignUp Model
    private SignUpModel signUpModel;
    // Presenter로 전달할 View 선언
    private SignUpPresenter.View view;

    public SignUpPresenterImpl(SignUpFragment signUpFragment) {
        this.signUpFragment = signUpFragment;
        // 아래를 정의하지 않으면 signUpModel.signUp(***)에서 NullPointerException이 나타남
        signUpModel = new SignUpModel();
    }

    @Override
    public void signUp() {
        String id = signUpFragment.etSuId.getText().toString();
        String pw = signUpFragment.etSuPw.getText().toString();
        String pwre = signUpFragment.etSuPwRe.getText().toString();
        String email = signUpFragment.etSuEmail.getText().toString();
        String name = signUpFragment.etSuName.getText().toString();
        String phone = signUpFragment.etSuPhone.getText().toString();
        Log.i(TAG, "----------- id ------- "+ id);
        Log.i(TAG, "----------- email ---- "+ email);
        signUpModel.signUp(id, pw, pwre, email, name, phone);
    }

    @Override
    public void setView(View view) {
        // Presenter로 전달할 View 정의
        this.view = view;
    }
}