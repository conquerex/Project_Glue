package com.hm.project_glue.sign.signup;

/**
 * Created by jongkook on 2016. 11. 30..
 */

public class SignUpPresenterImpl implements SignUpPresenter {
    // 모든 기능은 MVP를 따르며 현재 SignUpPresenterImpl가 Presenter 역할을 한다
    // SignUp View
    private SignUpFragment signUpFragment;
    // SignUp Model
    private SignUpModel signUpModel;

    @Override
    public void signUp() {
        String id = signUpFragment.etSuId.getText().toString();
        String pw = signUpFragment.etSuPw.getText().toString();
        String pwre = signUpFragment.etSuPwRe.getText().toString();
        String email = signUpFragment.etSuEmail.getText().toString();
        String name = signUpFragment.etSuName.getText().toString();
        String phone = signUpFragment.etSuPhone.getText().toString();
        signUpModel.signUp(id, pw, pwre, email, name, phone);
    }
}