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
    ListAdapter adapter;
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
        DBinit dbinit = new DBinit(getContext());


    }

    public void notifyDataSetChanged(){
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view   = inflater.inflate(R.layout.fragment_msg, container, false);
        ArrayList<NotiData> datas = new ArrayList<>();
        datas.addAll(DBinit.dbSelect());
        listView = (ListView) view.findViewById(R.id.msg_listView);
        LinearLayout lini1 = (LinearLayout) view.findViewById(R.id.msg_linear1);
        adapter = new ListAdapter(getContext(), datas, R.layout.msg_noti_item);
        listView.setAdapter(adapter);

        if(datas.size()==0){
            listView.setVisibility(View.GONE);
            lini1.setVisibility(View.VISIBLE);
        }
        return view;
    }


}
