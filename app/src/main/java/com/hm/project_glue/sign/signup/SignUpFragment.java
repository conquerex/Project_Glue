package com.hm.project_glue.sign.signup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hm.project_glue.R;

public class SignUpFragment extends Fragment implements SignUpPresenter.View {
    private static final String TAG = "SignUpFragment";
    Button btnSuSignUp;
    EditText etSuId, etSuPw, etSuPwRe, etSuEmail, etSuPhone, etSuName;
    private SignUpPresenter signUpPresenter;

    public SignUpFragment() {

    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MVP 패턴을 위해 View와 Presenter가 서로 참조할 수 있도록 함
        // View(SignUpFragment), Presenter(SignUpPresenter)
        // Presnter를 생성
        signUpPresenter = new SignUpPresenterImpl(SignUpFragment.this);
        // View 에서 발생하는 이벤트를 Presenter로 전달
        signUpPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        etSuId = (EditText)view.findViewById(R.id.etId);
        etSuPw = (EditText)view.findViewById(R.id.etPassword);
        etSuPwRe = (EditText)view.findViewById(R.id.etRePassword);
        etSuName = (EditText)view.findViewById(R.id.etName);
        etSuPhone = (EditText)view.findViewById(R.id.etPhone);
        etSuEmail = (EditText)view.findViewById(R.id.etEmail);
        btnSuSignUp = (Button)view.findViewById(R.id.btnSignUpSubmit);

        // 회원가입 버튼 클릭
        btnSuSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "----------- onClick");
                signUpPresenter.signUp();
            }
        });
        Log.i(TAG, "----------- fragment_sign_up-onCreateView");
        return view;
    }
}