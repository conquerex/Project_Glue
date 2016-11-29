package com.hm.project_glue.sign.signin;

/**
 * Created by HM on 2016-11-28.
 */

public class SignInPresenterImpl implements SignInPresenter {

    private SignInFragment fragment;

    private SignInPresenter.View view;

    public SignInPresenterImpl(SignInFragment fragment){
        this.fragment = fragment;
    }


    @Override
    public void setView(View view) {
        this.view = view;
    }
}
