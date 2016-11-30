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

public class SignUpFragment extends Fragment implements SignUpPresenter {

    Button btnSuSignUp;
    EditText etSuId, etSuPw, etSuPwRe, etSuEmail, etSuPhone, etSuName;

    public SignUpFragment() {

    }

    public static SignUpFragment newInstance() {
        SignUpFragment fragment = new SignUpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // 회원가입 버튼 클릭
        btnSuSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Log.i("test","fragment_sign_up-onCreateView");
        return view;
    }

    @Override
    public void signUp() {

    }
}