package com.hm.project_glue.sign.signup;

import android.widget.EditText;

/**
 * Created by jongkook on 2016. 11. 30..
 */

public interface SignUpPresenter {
    // 회원가입 기능
    void signUp();

    //
    boolean signUpCheck();

    void setView(SignUpPresenter.View view);

    // setView로 부르기 위해 임시로 만든 View
    public interface View {
        String getSuPhoneText();
        String getSuPwText();
        String getSuPwReText();
        String getSuNameText();
        String getSuEmailText();

        EditText getSuPhoneEditText();
        EditText getSuPwEditText();
        EditText getSuPwReEditText();
        EditText getSuNameEditText();
        EditText getSuEmailEditText();
    }
}
