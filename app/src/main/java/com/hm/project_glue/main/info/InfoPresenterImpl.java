package com.hm.project_glue.main.info;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.hm.project_glue.main.info.Data.InfoData;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.util.http.ListRestAdapter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by HM on 2016-11-29.
 */

public class InfoPresenterImpl implements InfoPresenter {

    private InfoPresenter.View view;
    private Context context;
    private InfoModel infoModel;
    private static String userId ="17"; // 임시 아이디 폰번호 ( 12121212 )
//    private InfoData infoData;
    public InfoPresenterImpl(InfoFragment fragment){
        this.context = fragment.getContext();
        infoModel = new InfoModel(fragment.getContext());
//        infoData = new InfoData();
        getUserInfo();
    }
    @Override
    public void setView(View view) { this.view = view; }




    @Override
    public void infoFormCheck(String phone, String name, String password1, String password2, String email, String img){
        String msg="";
        if(phone.length() < 8){
            msg = "Phone Number Minimum lenth 8";
        }else{
            if(name.length() < 5){
                msg = "Name Minimum lenth 8";
            }else{
                if(password1.length() < 13){
                    msg = "password Minimum lenth 13";
                }else{
                    if(!password1.equals(password2)){
                        msg = "password not same";
                    }else{
                        if(!isEmailPattern(email)){
                            msg = "email address mismatch";
                        }else{
                            // 모두 검증 통과
                            setUserInfoUpdate(phone, name, password1, email, img);
                            return;
                        }
                    }
                }
            }
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    private boolean isEmailPattern(String email){
        String pattern = "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+";
        return Pattern.matches(pattern, email.trim());
    }
    private void infoUpdate(String phone, String name, String password,  String email, String img){
        Log.i(TAG, "infoUpdate");
    }

    private void getUserInfo(){
        try {
            String authorization = "Token "+ Networking.getToken();
            final Call<InfoData> response = ListRestAdapter.getInstance().myInfoData(authorization);
            response.enqueue(new Callback<InfoData>() {
                @Override
                public void onResponse(Call<InfoData> call, Response<InfoData> response) {
                    if(response.isSuccessful()){
                        InfoData infoData = response.body();
                        view.setInfo(infoData);
                    }
                    Log.i(TAG, "myInfoData response Code : "+response.code());
                }
                @Override
                public void onFailure(Call<InfoData> call, Throwable t) {
                   Log.e(TAG, t.getMessage());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void setUserInfoUpdate(String phone_number, String name, String password,  String email, String img){
        try {
            String authorization = "Token "+ Networking.getToken();
            Map<String, RequestBody> bodyMap = new HashMap<>();

            //캐시 파일로 처리
//            File cache = context.getCacheDir();
//            String filename = "image.jpg";
//            File tmpFile = new File(cache, filename);
//            tmpFile.createNewFile();
//            FileOutputStream out = new FileOutputStream(tmpFile);
            // bitmap Byte로 처리
            Bitmap bitmap = imgReSizing(img);
            ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
            bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
            byte[] byteArray = stream.toByteArray();




            bodyMap.put("phone_number",RequestBody.create(MediaType.parse("multipart/form-data"), phone_number));
            bodyMap.put("name",RequestBody.create(MediaType.parse("multipart/form-data"), name));
            bodyMap.put("password",RequestBody.create(MediaType.parse("multipart/form-data"), password));
            bodyMap.put("email",RequestBody.create(MediaType.parse("multipart/form-data"), email));
            RequestBody body  = RequestBody.create(MediaType.parse("image/*"), byteArray, 0, byteArray.length);
            bodyMap.put("\"image\"; filename=\"profile.jpg\"", body);

            final Call<InfoData> response = ListRestAdapter.getInstance().myInfoUpdateData(authorization, bodyMap);
            response.enqueue(new Callback<InfoData>() {
                @Override
                public void onResponse(Call<InfoData> call, Response<InfoData> response) {
                    if(response.isSuccessful()){
                        InfoData infoData = response.body();
                        view.setInfo(infoData);
                    }
                    Log.i(TAG, "myInfoData response Code : "+response.code());
                }
                @Override
                public void onFailure(Call<InfoData> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap imgReSizing(String path){
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
            Log.i( TAG, "imagePath:"+imagePath);
            Log.i( TAG, "reqWidth:"+reqWidth+"/reqHeight:"+reqHeight+"/height:"+height+"/width:"+width);
            if (height > reqHeight || width > reqWidth) {
                options.inSampleSize = (width / reqWidth);
            }
            Log.i( TAG, "inSampleSize:"+inSampleSize);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(imagePath, options);


        }catch (Exception e ){
            Log.e(TAG, e.getMessage());
        }
        return bitmap;

    }




}
