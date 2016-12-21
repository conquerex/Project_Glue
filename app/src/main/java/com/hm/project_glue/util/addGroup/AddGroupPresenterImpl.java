package com.hm.project_glue.util.addGroup;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.hm.project_glue.main.home.data.HomeData;
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

/**
 * Created by jongkook on 2016. 12. 19..
 */

public class AddGroupPresenterImpl implements AddGroupPresenter {
    private static final String TAG = "AddGroupPresenterImpl";
    private Context context;
    AddGroupPresenter.View view;
    ProgressDialog progress;

    public AddGroupPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void setView(View view) {
        this.view = view;
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
    public void addGroupSave(Bitmap bitmap, String groupName) {
        progress = new ProgressDialog(context);
        progress.setMessage("Upload....");
        progress.setProgressStyle((ProgressDialog.STYLE_SPINNER));
        progress.setCancelable(false);
        progress.show();

        String authorization = "Token "+ Networking.getToken();
        String fileName="photo_"+groupName+".jpg";
        Log.i(TAG, "-------- addGroupSave : "+authorization);
        Map<String, RequestBody> imgMap = new HashMap<>();

        // 그룹 이미지 세팅
        if(bitmap!=null){
            Log.i(TAG, "-------- bitmap ");
            ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
            byte[] byteArray = stream.toByteArray();
            RequestBody body  = RequestBody.create(MediaType.parse("image/*"), byteArray, 0, byteArray.length);
            // key값 : group_image
            imgMap.put("group_image\"; filename=\""+fileName+"\"", body);
        }

        imgMap.put("group_name",RequestBody.create(MediaType.parse("multipart/form-data"), groupName));

        final Call<HomeData> response = ListRestAdapter.getInstance().createGroupData(authorization, imgMap);
        response.enqueue(new Callback<HomeData>() {
            @Override
            public void onResponse(Call<HomeData> call, Response<HomeData> response) {
                view.addGroupResult(response.code());
                Log.i(TAG, "-------- onResponse : "+groupName);
                if(response.isSuccessful()){
                    Toast.makeText(context, "Add Group : Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Add Group : Fail", Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG, "-------- photoAddGroupUpdate : "+response.code());
                view.addGroupFinish();
                progress.dismiss();
            }
            @Override
            public void onFailure(Call<HomeData> call, Throwable t) {
                Log.e(TAG, "------ " + t.getMessage());
                view.addGroupFinish();
                progress.dismiss();
            }
        });
    }
}
