package com.hm.project_glue.main.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.hm.project_glue.R;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-18.
 */

public class ListPhotoListAdapter extends BaseAdapter {


    //기본속성값 설정
    Context             context;     // 1. 컨텍스트
    ArrayList<String>   datas;       // 2. 데이터
    int                 layoutItem;    // 3. 레이아웃 아이템
    LayoutInflater      inflater;    // 4. 인플레이터
    int                 img_height;

    ListPhotoListAdapter(Context context, ArrayList<String> datas, int layoutItem){
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
        ProgressBar bar = (ProgressBar) convertView.findViewById(R.id.bar_list_item);
        if( datas.size() > 1){
            itemImg.setScaleType(ImageView.ScaleType.FIT_XY);

        }else{
            itemImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        Glide.with(context).load(imageurl).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                bar.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                bar.setVisibility(View.GONE);
                itemImg.setVisibility(View.VISIBLE);
                return false;
            }
        }).into(itemImg);



        return convertView;
    }
}
