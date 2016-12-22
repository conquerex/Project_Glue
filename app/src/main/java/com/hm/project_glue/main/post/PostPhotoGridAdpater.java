package com.hm.project_glue.main.post;

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
 * Created by HM on 2016-12-22.
 */


public class PostPhotoGridAdpater extends BaseAdapter {
    Context context;
    ArrayList<String> datas;
    int layoutItem;
    LayoutInflater inflater;
    public PostPhotoGridAdpater(Context context, ArrayList<String> datas, int layoutItem){
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


        Glide.with(context).load(imageurl).centerCrop().into(itemImg);

        return convertView;
    }

}

