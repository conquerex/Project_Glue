package com.hm.project_glue.sign.signup;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hm.project_glue.R;

public class SignUpFragment extends Fragment implements SignUpPresenter.View {
    private static final String TAG = "SignUpFragment";
    Button btnSuSignUp;
    EditText etSuPw, etSuPwRe, etSuEmail, etSuPhone, etSuName;
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
        // View 에서 발생하는 이벤트를 Presenter로 전달 <--- 이해가 안되는 부분
        signUpPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        // 2016.12.07 폼변경
        etSuPhone = (EditText)view.findViewById(R.id.etPhone);
        etSuPw = (EditText)view.findViewById(R.id.etPassword);
        etSuPwRe = (EditText)view.findViewById(R.id.etRePassword);
        etSuName = (EditText)view.findViewById(R.id.etName);
        etSuEmail = (EditText)view.findViewById(R.id.etEmail);
        btnSuSignUp = (Button)view.findViewById(R.id.btnSignUpSubmit);

        // 회원가입 버튼 클릭
        btnSuSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 휴대전화 입력 확인
                if( etSuPhone.getText().toString().length() == 0 ) {
                    Toast.makeText(view.getContext(),
                            R.string.signUpPhoneInputChk, Toast.LENGTH_SHORT).show();
                    etSuPhone.requestFocus();
                    return;
                }

                // 비밀번호 입력 확인
                if( etSuPw.getText().toString().length() == 0 ) {
                    Toast.makeText(view.getContext(),
                            R.string.signUpPwInputChk, Toast.LENGTH_SHORT).show();
                    etSuPw.requestFocus();
                    return;
                } else if ( etSuPw.getText().toString().length() < 8 ) {
                    Toast.makeText(view.getContext(),
                            R.string.signUpPwLengthChk, Toast.LENGTH_SHORT).show();
                    // EditText를 터치하지 않고 바로 입력할 수 있도록 requestFocus()를 사용
                    etSuPw.requestFocus();
                    return;
                }

                // 비밀번호 확인 입력 확인
                if( etSuPwRe.getText().toString().length() == 0 ) {
                    Toast.makeText(view.getContext(),
                            R.string.signUpPwReInputChk, Toast.LENGTH_SHORT).show();
                    etSuPwRe.requestFocus();
                    return;
                } else if ( etSuPwRe.getText().toString().length() < 8 ) {
                    Toast.makeText(view.getContext(),
                            R.string.signUpPwReLengthChk, Toast.LENGTH_SHORT).show();
                    // EditText를 터치하지 않고 바로 입력할 수 있도록 requestFocus()를 사용
                    etSuPwRe.requestFocus();
                    return;
                }

                // 비밀번호 일치 확인
                if( !etSuPw.getText().toString().equals(etSuPwRe.getText().toString()) ) {
                    Toast.makeText(view.getContext(),
                            R.string.signUpPwNoEqual, Toast.LENGTH_SHORT).show();
                    // 일치가 틀렸을 경우, 아래 주석을 풀면 입력된 사항을 지울 수 있다
                    // etSuPw.setText("");
                    // etSuPwRe.setText("");
                    etSuPwRe.requestFocus();
                    return;
                }

                // 이름 입력 확인
                if( etSuName.getText().toString().length() == 0 ) {
                    Toast.makeText(view.getContext(),
                            R.string.signUpNameInputChk, Toast.LENGTH_SHORT).show();
                    etSuName.requestFocus();
                    return;
                }

                // 이메일 입력 확인
                if( etSuEmail.getText().toString().length() == 0 ) {
                    Toast.makeText(view.getContext(),
                            R.string.signUpEmailInputChk, Toast.LENGTH_SHORT).show();
                    etSuEmail.requestFocus();
                    return;
                }
                Log.i(TAG, "----------- onClick");
                signUpPresenter.signUp();
                // 키보드 내리기
                InputMethodManager mgr = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
                // 로그인 화면으로 돌아가기
                Log.i(TAG, "----------- popBackStack");
                FragmentManager manager = getActivity().getSupportFragmentManager();
                // manager.beginTransaction().remove(SignUpFragment.this).commit();
                manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
        return view;
    }
    @Override
    public void onStop() {
        super.onStop();
        // 회원가입 후, 다시 회원가입 버튼 눌렀을 때 초기화
        etSuPhone.setText(null);
        etSuPw.setText(null);
        etSuPwRe.setText(null);
        etSuName.setText(null);
        etSuEmail.setText(null);
    }
}