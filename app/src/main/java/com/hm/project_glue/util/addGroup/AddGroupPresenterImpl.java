package com.hm.project_glue.util.addGroup;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.hm.project_glue.main.info.data.InfoData;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.util.http.ListRestAdapter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by jongkook on 2016. 12. 19..
 */

public class AddGroupPresenterImpl implements AddGroupPresenter {
    private static final String TAG = "AddGroupPresenterImpl";
    private Context context;
    AddGroupPresenter.View view;

    public AddGroupPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setView(View view) {
        this.view = view;
//        new AsyncTask<String, Void, Integer>(){
//            ProgressDialog progress;
//            @Override
//            protected Integer doInBackground(String... params) {
//                String result = "";
//                Log.i(TAG, "----------- doInBackground ");
//                try {
//                    // SERVER_URL에서 보낸 값을 받음
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Integer code) {
//                super.onPostExecute(code);
//                view.progressAddGroupShow(false);
//                Log.i(TAG,"---- onPostExecute");
//                view.addGroupResult(code);
//            }
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                view.progressAddGroupShow(false);
//                Log.i(TAG,"---- onPreExecute");
//            }
//        }.execute();
    }

    @Override
    public Bitmap imgAddGroupReSizing(String path) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int reqWidth = dm.widthPixels;
        int reqHeight = dm.heightPixels;
        String imagePath = path;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = null;
        try{
            BitmapFactory.decodeStream(new FileInputStream(imagePath), null, options);
            final int height = options.outHeight;
            final int width = options.outWidth;
            int inSampleSize = 1;
            Log.i( TAG, "---- imagePath:"+imagePath);
            Log.i( TAG, "\"---- reqWidth:"+reqWidth+"/reqHeight:"+reqHeight+"/height:"+height+"/width:"+width);
            if (height > reqHeight || width > reqWidth) {
                options.inSampleSize = (width / reqWidth);
            }
            Log.i( TAG, "\"---- inSampleSize:"+inSampleSize);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(imagePath, options);
        }catch (Exception e ){
            Log.e(TAG, e.getMessage());
        }
        return bitmap;
    }

    @Override
    public void addHttp() {

    }

    @Override
    public void photoAddGroupUpdate(Bitmap bitmap) {
        if(bitmap!=null){
            ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
            byte[] byteArray = stream.toByteArray();
            String authorization = "Token "+ Networking.getToken();
            Map<String, RequestBody> imgMap = new HashMap<>();
            RequestBody body  = RequestBody.create(MediaType.parse("image/*"), byteArray, 0, byteArray.length);
            imgMap.put("image\"; filename=\"profile.jpg", body);
            final Call<InfoData> response = ListRestAdapter.getInstance().myPhotoUpdateData(authorization, imgMap);
            response.enqueue(new Callback<InfoData>() {
                @Override
                public void onResponse(Call<InfoData> call, Response<InfoData> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(context, "Update Successful", Toast.LENGTH_SHORT).show();
                    }
                    Log.i(TAG, "-------- photoAddGroupUpdate : "+response.code());
                }
                @Override
                public void onFailure(Call<InfoData> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });
        }
    }
}
