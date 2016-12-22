package com.hm.project_glue.main.post;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hm.project_glue.R;
import com.hm.project_glue.main.list.data.Comments;
import com.hm.project_glue.util.Util;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


/**
 * Created by HM on 2016-12-22.
 */

public class PostDetailAdapter extends BaseAdapter {
    Context context;
    ArrayList<Comments> datas;
    int layoutItem;
    LayoutInflater inflater;
    public PostDetailAdapter(Context context, ArrayList<Comments> datas, int layoutItem){
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
        Comments comments = datas.get(position);
        if(convertView == null){
            convertView = inflater.inflate(layoutItem, null);
        }

        ImageView itemImg = (ImageView) convertView.findViewById(R.id.post_imgComment);
        TextView name = ( TextView ) convertView.findViewById(R.id.post_commentName);
        TextView time = ( TextView ) convertView.findViewById(R.id.post_CommentTime);
        TextView contents = ( TextView ) convertView.findViewById(R.id.post_commentCon);
        name.setText(comments.getUser().getName());
        contents.setText(comments.getContent());
        String getTime = Util.timeChange(context, comments.getCreated_date());
        time.setText(getTime);

      if( comments.getUser().getImage()!=null){
          Glide.with(context).load(comments.getUser().getImage()).bitmapTransform(new CropCircleTransformation(context))
                  .into(itemImg);
      }else{
          Glide.with(context).load(R.drawable.noprofile).bitmapTransform(new CropCircleTransformation(context))
                  .into(itemImg);
      }
        return convertView;
    }

}
