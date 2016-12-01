package com.hm.project_glue.sign.signup;

/**
 * Created by jongkook on 2016. 11. 30..
 */

public interface SignUpPresenter {
    // 회원가입 기능
    void signUp();

    void setView(SignUpPresenter.View view);

    // setView로 부르기 위해 임시로 만든 View
    public interface View {

    }
}
