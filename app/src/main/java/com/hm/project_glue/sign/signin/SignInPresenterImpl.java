package com.hm.project_glue.sign.signin;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.hm.project_glue.service.notification.MyFirebaseInstanceIDService;
import com.hm.project_glue.util.Networking;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

public class SignInPresenterImpl implements SignInPresenter {

    private SignInModel signInModel;
    private SignInPresenter.View view;
    private final int ID_MIN_LENGTH = 5;
    private final int PW_MIN_LENGTH = 8;
    private final static String TAG ="TEST";
    Context context;
    public SignInPresenterImpl(SignInFragment fragment){
        signInModel = new SignInModel(fragment.getContext());
        context = fragment.getActivity();
    }
    @Override
    public void setView(View view) {
        this.view = view;
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
    public void signIn(){
        MyFirebaseInstanceIDService service  = new MyFirebaseInstanceIDService();
        service.onTokenRefresh();
        String device_token = service.getToken();
        HashMap userInfoMap = new HashMap();
        String id = view.getIdText();
        String pw = view.getPwText();
        userInfoMap.put("phone_number", id);
        userInfoMap.put("password", pw);
        userInfoMap.put("device_token", device_token);
        userInfoMap.put("device_type", "android");

        new AsyncTask<Map, Void, String>(){
            ProgressDialog progress;
            @Override
            protected String doInBackground(Map... params) {
                String result = "";
                try {
                    result = signInModel.postData(params[0]);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return result;
            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progress = new ProgressDialog(context);
                progress.setMessage("Loging....");
                progress.setProgressStyle((ProgressDialog.STYLE_SPINNER));
                progress.setCancelable(false);
                progress.show();
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                String token ="";

                    try {
                        JSONObject jObject = new JSONObject(result);
                        token = jObject.getString("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (!token.equals("")) { // 로그인 성공
                        Log.i(TAG, "if:" + token);
                        // Preferences 에 token 저장
                        signInModel.savePreferences(id, token);
                        view.moveActivity();
                    } else {// 로그인 실패
                        Log.i(TAG, "else:" + token);
                        view.failAlert(Networking.getResponseCode());
                        view.reSetEditText();
                    }

                progress.dismiss();

            }
        }.execute(userInfoMap);


    }

    @Override
    public void observableInit() {
        Observable<TextViewTextChangeEvent> idObs
                =  RxTextView.textChangeEvents(view.getEditTextId());
        Observable<TextViewTextChangeEvent> passObs
                =  RxTextView.textChangeEvents(view.getEditTextPw());

        Observable.combineLatest(idObs,passObs,
                (idChanges,passChanges) -> {
                    boolean idCheck = idChanges.text().length() >= ID_MIN_LENGTH;
                    boolean passCheck = passChanges.text().length() >= PW_MIN_LENGTH;
                    return idCheck && passCheck;
                })
                .subscribe(
                        checkFlag -> view.setButtonEnabled(checkFlag)
                );
    }

}
