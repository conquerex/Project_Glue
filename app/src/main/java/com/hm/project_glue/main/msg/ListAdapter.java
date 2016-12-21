package com.hm.project_glue.main.msg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hm.project_glue.R;
import com.hm.project_glue.main.msg.data.NotiData;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-19.
 */

public class ListAdapter extends BaseAdapter {
    Context context;
    ArrayList<NotiData> datas;
    int layoutItem;
    LayoutInflater      inflater;
    ListAdapter(Context context, ArrayList<NotiData> datas,  int layoutItem){
        this.context=context;
        this.datas=datas;
        this.layoutItem=layoutItem;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(layoutItem, null);
        }
        NotiData noti = datas.get(position);

        TextView title =(TextView) convertView.findViewById(R.id.msg_Title);
        TextView contents =(TextView) convertView.findViewById(R.id.msg_Contents);

        title.setText(noti.getTitle());
        contents.setText(noti.getContents());

        return convertView;
    }
}
