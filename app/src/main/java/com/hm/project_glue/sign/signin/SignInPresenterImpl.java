package com.hm.project_glue.sign.signin;


import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import rx.Observable;

public class SignInPresenterImpl implements SignInPresenter {

    private SignInFragment fragment;
    private SignInModel signInModel;
    private SignInPresenter.View view;
    private final int ID_MIN_LENGTH = 5;
    private final int PW_MIN_LENGTH = 8;

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

    @Override
    public void observableInit() {
        Observable<TextViewTextChangeEvent> idObs
                =  RxTextView.textChangeEvents(fragment.etId);
        Observable<TextViewTextChangeEvent> passObs
                =  RxTextView.textChangeEvents(fragment.etPasswd);

        Observable.combineLatest(idObs,passObs,
                (idChanges,passChanges) -> {
                    boolean idCheck = idChanges.text().length() >= ID_MIN_LENGTH;
                    boolean passCheck = passChanges.text().length() >= PW_MIN_LENGTH;
                    return idCheck && passCheck;
                })
                .subscribe(
                        checkFlag -> fragment.btnSignIn.setEnabled(checkFlag)
                );
    }


}
