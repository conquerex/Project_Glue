package com.hm.project_glue.main.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hm.project_glue.R;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-21.
 */

public class ListPhotoAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> datas;
    int layoutItem;
    LayoutInflater inflater;
    public ListPhotoAdapter(Context context, ArrayList<String> datas, int layoutItem){
        this.context = context;
        this.datas = datas;
        this.layoutItem = layoutItem;
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
        String imageurl = datas.get(position);
        if(convertView == null){
            convertView = inflater.inflate(layoutItem, null);
        }

        ImageView itemImg = (ImageView) convertView.findViewById(R.id.img_list_item);
//        ProgressBar bar = (ProgressBar) convertView.findViewById(R.id.bar_list_item);
        if( datas.size() > 1){
            Glide.with(context).load(imageurl).override(350,200).centerCrop().into(itemImg);
        }else{  // 이미지가 1개일 때
            Glide.with(context).load(imageurl).override(700,400).centerCrop().into(itemImg);
        }
        return convertView;
    }

}
