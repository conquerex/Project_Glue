package com.hm.project_glue.util.write;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hm.project_glue.R;
import com.hm.project_glue.util.write.data.GroupListResults;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-12.
 */


public class PopupListAdapter extends BaseAdapter{
    ArrayList<GroupListResults> datas;
    LayoutInflater inflater;
    Context context;


    PopupListAdapter(Context context, ArrayList<GroupListResults> datas){ //생성자
        this.context = context;
        this.datas = datas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() { // 자식 뷰들의 개수를 리턴해준다.
        return datas.size();
    }

    @Override
    public Object getItem(int position) {   // 자식 뷰를 리턴해준다.
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {   // 자식 뷰의 ID 값을 리턴해준다.
        return position;
    }

    @Override
    public android.view.View getView(int position, android.view.View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.grouplist_custom_item, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.groupListTextViewItem);
        textView.setText(datas.get(position).getGroup_name());

        convertView.setOnClickListener(v ->{
            ((WriteActivity)context).groupChanged(datas.get(position).getId(),datas.get(position).getGroup_name());
        });
        return convertView;
    }



}

