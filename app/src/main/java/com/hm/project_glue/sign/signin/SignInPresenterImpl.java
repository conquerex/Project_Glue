package com.hm.project_glue.sign.signin;

/**
 * Created by HM on 2016-11-28.
 */

public class SignInPresenterImpl implements SignInPresenter {

    private SignInFragment fragment;
    private SignInModel signInModel;
    private SignInPresenter.View view;

    public SignInPresenterImpl(SignInFragment fragment){
        this.fragment = fragment;
        signInModel = new SignInModel();

    }

//    public void progress() {
//        progress = new ProgressDialog(MainActivity.this);
//        progress.setTitle("다운로드");
//        progress.setMessage("download");
//        progress.setProgressStyle((ProgressDialog.STYLE_SPINNER));
//        progress.setCancelable(false);
//        progress.show();
//    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void signIn() {
        String id = fragment.etId.getText().toString();
        String pw = fragment.etPasswd.getText().toString();
        signInModel.signIn(id, pw);
    }
}
