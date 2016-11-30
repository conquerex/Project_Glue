package com.hm.project_glue.sign.signup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hm.project_glue.R;



public class SignUpFragment extends Fragment {

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

        Log.i("test","fragment_sign_up-onCreateView");
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }



}
