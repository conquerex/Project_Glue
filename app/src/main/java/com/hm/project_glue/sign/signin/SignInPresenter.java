package com.hm.project_glue.sign.signin;

/**
 * Created by HM on 2016-11-28.
 */

public interface SignInPresenter {

    void setView(SignInPresenter.View view);
    void signIn();
    void observableInit();

    public interface View {

    }
}
