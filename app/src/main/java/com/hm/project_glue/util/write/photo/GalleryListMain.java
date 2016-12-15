package com.hm.project_glue.util.write.photo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import com.hm.project_glue.R;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class GalleryListMain extends Activity {
    ProgressDialog mLoagindDialog;
    GridView mGvImageList;
    ImageAdapter mListAdapter;
    ArrayList<GalleryItem> mThumbImageInfoList;
    HashMap<String, String> pathMap;
    ArrayList<String> pathList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_list);
        mThumbImageInfoList = new ArrayList<>();
        pathMap = new HashMap<>();
        pathList = new ArrayList<>();
        mGvImageList = (GridView) findViewById(R.id.gvImageList);
        Button btnSelectOk = (Button) findViewById(R.id.btnSelectOk);
        btnSelectOk.setOnClickListener(v -> {

            Intent intent = new Intent();
            if( pathMap.size() > 0 ) {
                for (String str : pathMap.values()) {
                    pathList.add(str);
                }
                intent.putStringArrayListExtra("pathList", pathList);
            }
            setResult(1,intent);
            finish();
        });
        Button btnSelectCancel = (Button) findViewById(R.id.btnSelectCancel);
        btnSelectCancel.setOnClickListener(v ->{
            this.finish();
        });
        new DoFindImageList().execute();
    }

    private long findThumbList() {
        long returnValue = 0;

        String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA };

        Cursor imageCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.Media.DATE_ADDED + " desc ");

        if (imageCursor != null && imageCursor.getCount() > 0) {
            // 컬럼 인덱스
            int imageIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
            int imageDataCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);


            while (imageCursor.moveToNext()) {
                GalleryItem thumbInfo = new GalleryItem();

                thumbInfo.setId(imageCursor.getString(imageIDCol));
                thumbInfo.setData(imageCursor.getString(imageDataCol));

                String path = imageCursor.getString(imageDataCol);

                BitmapFactory.Options option = new BitmapFactory.Options();

                if (new File(path).length() > 100000)
                    option.inSampleSize = 10;
                else
                    option.inSampleSize = 2;
                thumbInfo.setPath(path);
                Bitmap bmp = BitmapFactory.decodeFile(path, option);
                thumbInfo.setBitmap(bmp);

                thumbInfo.setCheckedState(false);

                mThumbImageInfoList.add(thumbInfo);
                returnValue++;
            }
        }
        imageCursor.close();
        return returnValue;
    }

    private void updateUI() {
        mListAdapter = new ImageAdapter(this, R.layout.gallery_cell, mThumbImageInfoList);
        mGvImageList.setAdapter(mListAdapter);
    }

    static class ImageViewHolder {
        ImageView ivImage;
        CheckBox chkImage;
    }

    private class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private int mCellLayout;
        private LayoutInflater mLiInflater;
        private ArrayList<GalleryItem> mArrData;
        int selectLimit = 5;
        int selectCnt=1;

        public ImageAdapter(Context c, int cellLayout,
                            ArrayList<GalleryItem> thumbImageInfoList) {
            mContext = c;
            mCellLayout = cellLayout;
            mArrData = thumbImageInfoList;
            mLiInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return mArrData.size();
        }

        public Object getItem(int position) {
            return mArrData.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            if (convertView == null) {
                convertView = mLiInflater.inflate(mCellLayout, parent, false);
                ImageViewHolder holder = new ImageViewHolder();
                holder.ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
                holder.chkImage = (CheckBox) convertView.findViewById(R.id.chkImage);
                convertView.setTag(holder);
            }
            final ImageViewHolder holder = (ImageViewHolder) convertView.getTag();
            if ( mArrData.get(position).getCheckedState())
                holder.chkImage.setChecked(true);
            else
                holder.chkImage.setChecked(false);
            holder.ivImage.setImageBitmap(mArrData.get(position).getBitmap());
            setProgressBarIndeterminateVisibility(false);
            String key = mArrData.get(position).getId();
            String path =   mArrData.get(position).getPath();
            convertView.setOnClickListener(v -> {
                if (mArrData.get(position).getCheckedState() == true) {
                    mArrData.get(position).setCheckedState(false);
                    pathMap.remove(key);
                    selectCnt--;
                } else {
                    if(selectLimit >= selectCnt){
                        selectCnt++;
                        pathMap.put(key, path);
                        mArrData.get(position).setCheckedState(true);
                    }
                }
                mListAdapter.notifyDataSetChanged();
            });
            return convertView;
        }
    }


    private class DoFindImageList extends AsyncTask<String, Integer, Long> {
        @Override
        protected void onPreExecute() {
            mLoagindDialog = ProgressDialog.show(GalleryListMain.this, null, "Loding...", true, true);
            super.onPreExecute();
        }

        @Override
        protected Long doInBackground(String... arg0) {
            long returnValue = 0;
            returnValue = findThumbList();
            return returnValue;
        }

        @Override
        protected void onPostExecute(Long result) {
            updateUI();
            mLoagindDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

}
