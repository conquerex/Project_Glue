package com.hm.project_glue.sign.signin;

import android.app.Activity;

/**
 * Created by HM on 2016-11-28.
 */

public interface SignInPresenter {

    void setView(SignInPresenter.View view);
    void signIn(Activity activity);
    void observableInit();

    public interface View {

    }
}
