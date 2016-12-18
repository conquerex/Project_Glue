package com.hm.project_glue.main.msg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hm.project_glue.R;
import com.hm.project_glue.main.OnFragmentInteractionListener;


public class MsgFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public MsgFragment() {

    }

    public static MsgFragment newInstance() {
        MsgFragment fragment = new MsgFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_msg, container, false);
    }

}
