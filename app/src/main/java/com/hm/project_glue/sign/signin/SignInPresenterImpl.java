package com.hm.project_glue.sign.signin;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.hm.project_glue.R;
import com.hm.project_glue.sign.SignActivity;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

public class SignInPresenterImpl implements SignInPresenter {
    private SignInFragment fragment;
    private SignInModel signInModel;
    private SignInPresenter.View view;
    private final int ID_MIN_LENGTH = 5;
    private final int PW_MIN_LENGTH = 8;
    private final static String TAG ="TEST";
    Context context;

    public SignInPresenterImpl(SignInFragment fragment){
        this.fragment = fragment;
        signInModel = new SignInModel(fragment.getContext());
        context = fragment.getActivity();


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

        HashMap userInfoMap = new HashMap();
        String id = fragment.etId.getText().toString();
        String pw = fragment.etPasswd.getText().toString();
        userInfoMap.put("phone_number", id);
        userInfoMap.put("password", pw);

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
//                progress.setTitle("LOGIN");
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
                progress.dismiss();

                if(!token.equals("")) { // 로그인 성공
                    Log.i(TAG,"if:"+token);
                    // Preferences 에 token 저장
                    SharedPreferences loginCheck = fragment.getActivity().getSharedPreferences("localLoginCheck", 0);
                    SharedPreferences.Editor editor = loginCheck.edit();
                    editor.putString("id", id);
                    editor.putString("token", token);
                    editor.putBoolean("SIGN", true);
                    editor.commit();

                    // Activity 이동
                    ((SignActivity)fragment.getActivity()).moveActivity();
                }
                else{// 로그인 실패
                    Log.i(TAG,"else:"+token);
                    failAlert();
                    fragment.etId.setText("");
                    fragment.etPasswd.setText("");
                    fragment.etId.requestFocus();
                }

            }
        }.execute(userInfoMap);


    }
    @Override
    public void setView(View view) {
        this.view = view;
    }




    public void failAlert(){

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(R.string.loginfailtitle); // "로그인실패"
        alert.setMessage(R.string.loginfailmessage); // "아이디와 ..."
//        alert.setIcon(R.drawable.ic_launcher);
        alert.setNegativeButton(R.string.ok, null); // "확인"
        alert.show();

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
