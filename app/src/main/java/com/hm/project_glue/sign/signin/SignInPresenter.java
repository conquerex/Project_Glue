package com.hm.project_glue.sign.signin;

import android.widget.EditText;

/**
 * Created by HM on 2016-11-28.
 */

public interface SignInPresenter {

    void setView(SignInPresenter.View view);
    void signIn();
    void observableInit();

    interface View {
        void reSetEditText();
        void failAlert(int errorCode);
        void moveActivity();
        void setButtonEnabled(boolean flag);
        String getIdText();
        String getPwText();
        EditText getEditTextId();
        EditText getEditTextPw();
    }
}
