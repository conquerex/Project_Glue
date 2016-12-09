package com.hm.project_glue.sign.signin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hm.project_glue.R;
import com.hm.project_glue.sign.SignActivity;


public class SignInFragment extends Fragment implements SignInPresenter.View {
    private Button btnSignIn, btnSignUp, btnFindId, btnFacebook;
    private EditText etId, etPasswd;
    private SignInPresenter signInPresenter;

    public SignInFragment() {

    }

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInPresenter = new SignInPresenterImpl(SignInFragment.this);
        signInPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        etId = (EditText) view.findViewById(R.id.etId);
        etPasswd = (EditText) view.findViewById(R.id.etPasswd);
        btnSignIn = (Button) view.findViewById(R.id.btnSignIn);
        btnSignIn.setEnabled(false);
        btnSignUp = (Button) view.findViewById(R.id.btnSignUp);
        btnFindId = (Button) view.findViewById(R.id.btnFindId);
        btnFacebook = (Button) view.findViewById(R.id.btnFacebook);


        btnFindId.setOnClickListener(v-> {
            //TODO 임시 main activity 접근
            moveActivity();

        });


        btnSignUp.setOnClickListener(v -> {
            Log.i("test","click");
            ((SignActivity)getActivity()).goToSignUpFragment();
        });

        btnSignIn.setOnClickListener(v -> signInPresenter.signIn());
        btnFacebook.setOnClickListener(v -> ((SignActivity)getActivity()).facebookLoginOnClick(getView()));
        signInPresenter.observableInit();

        return view;
    }
    @Override
    public String getIdText() {
        return etId.getText().toString();
    }
    @Override
    public String getPwText() {
        return etPasswd.getText().toString();
    }
    @Override
    public EditText getEditTextId() {
        return etId;
    }
    @Override
    public EditText getEditTextPw() {
        return etPasswd;
    }


    @Override
    public void setButtonEnabled(boolean flag) {
        btnSignIn.setEnabled(flag);
    }

    @Override
    public void reSetEditText() {
        etId.setText("");
        etPasswd.setText("");
        etId.requestFocus();
    }

    @Override
    public void failAlert(int errorCode){

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(R.string.loginfailtitle); // "로그인실패"
        switch (errorCode){
            case 400 :  alert.setMessage(R.string.loginfailmessage+"(400)"); // "아이디와 ..."
                break;
            case 500 : alert.setMessage(R.string.loginHttpError+"(500)"); // "통신 오류"
                break;
            case 404 : alert.setMessage("not found 404"); // "통신 오류"
        }


        alert.setNegativeButton(R.string.ok, null); // "확인"
        alert.show();
    }

    @Override
    public void moveActivity() {
        ((SignActivity)getActivity()).moveActivity();
    }

}
