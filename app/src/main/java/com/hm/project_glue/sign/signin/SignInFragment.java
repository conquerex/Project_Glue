package com.hm.project_glue.sign.signin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hm.project_glue.R;
import com.hm.project_glue.sign.SignActivity;


public class SignInFragment extends Fragment implements SignInPresenter.View {
    Button btnSignIn, btnSignUp, btnFindId, btnFacebook;
    EditText etId, etPasswd;
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



        btnSignUp.setOnClickListener(v -> {
            Log.i("test","click");
            ((SignActivity)getActivity()).goToSignUpFragment();
        });

        btnSignIn.setOnClickListener(v -> signInPresenter.signIn(getActivity()));
        btnFacebook.setOnClickListener(v -> ((SignActivity)getActivity()).facebookLoginOnClick(getView()));
        signInPresenter.observableInit();

        return view;
    }




}
