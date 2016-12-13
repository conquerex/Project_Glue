package com.hm.project_glue.util.write;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hm.project_glue.R;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-10.
 */

public class PhotosListAdapter extends BaseAdapter {

    //기본속성값 설정
    Context             context;     // 1. 컨텍스트
    ArrayList<String>   datas;       // 2. 데이터
    int                 gridItem;    // 3. 레이아웃 아이템
    LayoutInflater      inflater;    // 4. 인플레이터
    int                 img_height;

    PhotosListAdapter(Context context, ArrayList<String> datas, int gridItem){
        this.context = context;
        this.datas = datas;
        this.gridItem = gridItem;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        img_height = dpToPx(context, 100);

    }
    public void setPathList(ArrayList<String> paths){
        this.datas = paths;
    }
    public int dpToPx(Context context, int dp){
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (metrics.xdpi / metrics.DENSITY_DEFAULT));
        return px;
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
        String imagePath = datas.get(position);
        // 1. 뷰를 생성,  있으면 그냥 사용
        if(convertView == null){
            convertView = inflater.inflate(gridItem, null);
        }
        ImageView itemImg = (ImageView) convertView.findViewById(R.id.imgGridItem);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap src = BitmapFactory.decodeFile(imagePath, options);
        Bitmap resized = Bitmap.createScaledBitmap(src, 400, 500, true);

        Log.i("TEST", "img_height:"+img_height);


//        Bitmap resize = Bitmap.createScaledBitmap(image_bitmap,image_bitmap.getWidth(),img_height,true);
        itemImg.setImageBitmap(resized);
        return convertView;
    }
}
