package com.hm.project_glue.sign.signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.hm.project_glue.R;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by jongkook on 2016. 11. 30..
 */

public class SignUpPresenterImpl implements SignUpPresenter {
    private static final String TAG = "SignUpPresenterImpl";
    private static final int PASSWORD_MIN = 13;

    // 모든 기능은 MVP를 따르며 현재 SignUpPresenterImpl가 Presenter 역할을 한다
    // SignUp Model
    private SignUpModel signUpModel;
    // Presenter로 전달할 View 선언
    private SignUpPresenter.View view;
    Context context;

    public SignUpPresenterImpl(SignUpFragment signUpFragment) {
        signUpModel = new SignUpModel(signUpFragment.getContext());
        context = signUpFragment.getActivity();
    }

    @Override
    public void setView(View view) {
        // Presenter로 전달할 View 정의
        this.view = view;
    }

    @Override
    public void signUp() {
        // 2016.12.10 MVP 패턴 적용으로 인한 수정
        String phone = view.getSuPhoneText();
        String pw = view.getSuPwText();
        String pwre = view.getSuPwReText();
        String email = view.getSuEmailText();
        String name = view.getSuNameText();
        Log.i(TAG, "----------- phone ---- "+ phone);
        Log.i(TAG, "----------- email ---- "+ email);

        HashMap hashMap = new HashMap();
        hashMap.put("phone_number", phone);
        hashMap.put("password", pw);
        hashMap.put("name", name);
        hashMap.put("email", email);

        // AsyncTask클래스는 항상 Subclassing 해서 사용 해야 함.
        // UI 처리 및 Background 작업 등 을 하나의 클래스에서 작업 할 수 있게 지원
        // 파라미터 타입은 작업 실행 시에 송신 : Map (doInBackground 파라미터 타입이, execute 메소드 인자값)
        // doInBackground 작업 시 진행 단위의 타입 : Void (onProgressUpdate 파라미터 타입)
        // doInBackground 리턴값 : String (onPostExecute 파라미터 타입)
        // 인자를 사용하지 않은 경우 Void Type 으로 지정
        new AsyncTask<Map, Void, String>(){
            ProgressDialog progress;
            @Override
            // doInBackground : Background 작업을 진행
            // doInBackground의 매개값 : Map
            protected String doInBackground(Map... params) {
                String result = "";
                Log.i(TAG, "----------- doInBackground ");
                try {
                    // SERVER_URL에서 보낸 값을 받음
                    result = signUpModel.postData(params[0]);
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
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i(TAG, "----------- onPostExecute ---- " + s);

                try {
                    Log.i(TAG, "----------- 로그인 성공");
                    progress.dismiss();
                    Toast.makeText(context, "회원가입 완료", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.i(TAG, "----------- 로그인 실패");
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle(R.string.loginfailtitle); // "로그인실패"
                    alert.setMessage(R.string.loginfailmessage); // "아이디와 ..."
                    alert.setNegativeButton(R.string.ok, null); // "확인"
                    alert.show();
                    e.printStackTrace();
                }
            }
        }.execute(hashMap);
        // signUpModel.signUp(phone, pw, pwre, email, name);
    }

    // 2016.12.10 회원가입 조건 체크
    @Override
    public boolean signUpCheck() {
        // 휴대전화 입력 확인
        if( view.getSuPhoneText().length() == 0 ) {
            Toast.makeText(context,
                    R.string.signUpPhoneInputChk, Toast.LENGTH_SHORT).show();
            view.getSuPhoneEditText().requestFocus();
            return false;
        }

        // 비밀번호 입력 확인
        if( view.getSuPwText().length() == 0 ) {
            Toast.makeText(context,
                    R.string.signUpPwInputChk, Toast.LENGTH_SHORT).show();
            view.getSuPwEditText().requestFocus();
            return false;
        } else if ( view.getSuPwText().length() < PASSWORD_MIN ) {
            Toast.makeText(context,
                    R.string.signUpPwLengthChk, Toast.LENGTH_SHORT).show();
            // EditText를 터치하지 않고 바로 입력할 수 있도록 requestFocus()를 사용
            view.getSuPwEditText().requestFocus();
            return false;
        }

        // 비밀번호 확인 입력 확인
        if( view.getSuPwReText().length() == 0 ) {
            Toast.makeText(context,
                    R.string.signUpPwReInputChk, Toast.LENGTH_SHORT).show();
            view.getSuPwReEditText().requestFocus();
            return false;
        } else if ( view.getSuPwReText().length() < PASSWORD_MIN ) {
            Toast.makeText(context,
                    R.string.signUpPwReLengthChk, Toast.LENGTH_SHORT).show();
            // EditText를 터치하지 않고 바로 입력할 수 있도록 requestFocus()를 사용
            view.getSuPwReEditText().requestFocus();
            return false;
        }

        // 비밀번호 일치 확인
        if( !view.getSuPwText().equals(view.getSuPwReText()) ) {
            Toast.makeText(context,
                    R.string.signUpPwNoEqual, Toast.LENGTH_SHORT).show();
            // 일치가 틀렸을 경우, 아래 주석을 풀면 입력된 사항을 지울 수 있다
            // etSuPw.setText("");
            // etSuPwRe.setText("");
            view.getSuPwReEditText().requestFocus();
            return false;
        }

        // 이름 입력 확인
        if( view.getSuNameText().length() == 0 ) {
            Toast.makeText(context,
                    R.string.signUpNameInputChk, Toast.LENGTH_SHORT).show();
            view.getSuNameEditText().requestFocus();
            return false;
        }

        // 이메일 포맷 체크
        boolean emailCheck = Pattern.matches(
                "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+", view.getSuEmailText().trim());
        Log.i(TAG, "----------- emailCheck ----- " + emailCheck);

        // 이메일 입력 확인
        if( view.getSuEmailText().length() == 0 ) {
            Toast.makeText(context,
                    R.string.signUpEmailInputChk, Toast.LENGTH_SHORT).show();
            view.getSuEmailEditText().requestFocus();
            return false;
        }else if(!emailCheck){
            Toast.makeText(context,
                    R.string.signUpEmailPattern, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}