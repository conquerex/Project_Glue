package com.hm.project_glue.main.msg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hm.project_glue.R;
import com.hm.project_glue.main.msg.data.NotiData;

import java.util.ArrayList;


public class MsgFragment extends Fragment {

    private ListView listView;
    private ArrayList<NotiData> datas;
    public MsgFragment() {

    }

    public static MsgFragment newInstance() {
        MsgFragment fragment = new MsgFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas = new ArrayList<>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view   = inflater.inflate(R.layout.fragment_msg, container, false);


        listView = (ListView) view.findViewById(R.id.msg_listView);
        LinearLayout lini1 = (LinearLayout) view.findViewById(R.id.msg_linear1);


        if(datas.size()==0){
            listView.setVisibility(View.GONE);
            lini1.setVisibility(View.VISIBLE);
        }


        return view;
    }

}
