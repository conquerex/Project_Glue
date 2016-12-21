package com.hm.project_glue.main.info;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.target.SquaringDrawable;
import com.facebook.login.widget.ProfilePictureView;
import com.hm.project_glue.main.info.data.InfoData;
import com.hm.project_glue.main.list.data.NotiJson;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.util.http.ListRestAdapter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    public InfoPresenterImpl(InfoFragment fragment){
        this.context = fragment.getContext();
        infoModel = new InfoModel(fragment.getContext());
        getUserInfo();
    }
    @Override
    public void setView(View view) { this.view = view; }

    @Override
    public void infoFormCheck(String phone, String name, String password1, String password2, String email){
        String msg="";
        if(phone.length() < 8){
            msg = "Phone Number Minimum lenth 8";
        }else{
            if(name.length() < 3){
                    msg = "Name Minimum lenth 3";
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
                                setUserInfoUpdate(phone, name, password1, email);
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
    public Bitmap getBitmap(ImageView imgView){
        Bitmap bitmap = null;
        Drawable drawable = imgView.getDrawable();
        if (drawable instanceof GlideBitmapDrawable) {
            bitmap = ((GlideBitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof TransitionDrawable) {
            TransitionDrawable transitionDrawable = (TransitionDrawable) drawable;
            int length = transitionDrawable.getNumberOfLayers();
            for (int i = 0; i < length; ++i) {
                Drawable child = transitionDrawable.getDrawable(i);
                if (child instanceof GlideBitmapDrawable) {
                    bitmap = ((GlideBitmapDrawable) child).getBitmap();
                    break;
                } else if (child instanceof SquaringDrawable
                        && child.getCurrent() instanceof GlideBitmapDrawable) {
                    bitmap = ((GlideBitmapDrawable) child.getCurrent()).getBitmap();
                    break;
                }
            }
        } else if (drawable instanceof SquaringDrawable) {
            bitmap = ((GlideBitmapDrawable) drawable.getCurrent()).getBitmap();
        }
        return bitmap;
    }
    public void photoUpdate(Bitmap bitmap, String fileName){

        if( bitmap !=null){
            Map<String, RequestBody> imgMap = new HashMap<>();
            String authorization = "Token "+ Networking.getToken();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            RequestBody body = RequestBody.create(MediaType.parse("image/*"), byteArray, 0, byteArray.length);
            imgMap.put("image\"; filename=\""+fileName, body);


            final Call<InfoData> response = ListRestAdapter.getInstance().myPhotoUpdateData(authorization, imgMap);
            response.enqueue(new Callback<InfoData>() {
                @Override
                public void onResponse(Call<InfoData> call, Response<InfoData> response) {
                    if(response.isSuccessful()){
                        view.toast("upload");
                    }
                    Log.i(TAG, "myInfoData response Code : "+response.code());
                }
                @Override
                public void onFailure(Call<InfoData> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });
        }else{
            view.toast("Bitmap is null");
        }

    }
    @Override
    public void callHttpNoti(NotiJson notiJson){
        try {
            String authorization = "Token "+ Networking.getToken();
            final Call<InfoData> response = ListRestAdapter.getInstance().myNotiUpdateData(authorization, notiJson);
            response.enqueue(new Callback<InfoData>() {
                @Override
                public void onResponse(Call<InfoData> call, Response<InfoData> response) {
                    if(response.isSuccessful()){
                        InfoData infoData = response.body();
                        view.setInfo(infoData);
                        view.toast("Update Successful");
                    }
                    Log.i(TAG, "myInfoData response Code : "+response.code());
                }
                @Override
                public void onFailure(Call<InfoData> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                    view.toast("Update fail");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setUserInfoUpdate(String phone_number, String name, String password,  String email){
        try {
            String authorization = "Token "+ Networking.getToken();
            Map<String, RequestBody> bodyMap = new HashMap<>();
            bodyMap.put("phone_number",RequestBody.create(MediaType.parse("multipart/form-data"), phone_number));
            bodyMap.put("name",RequestBody.create(MediaType.parse("multipart/form-data"), name));
            bodyMap.put("password",RequestBody.create(MediaType.parse("multipart/form-data"), password));
            bodyMap.put("email",RequestBody.create(MediaType.parse("multipart/form-data"), email));
            final Call<InfoData> response = ListRestAdapter.getInstance().myInfoUpdateData(authorization, bodyMap);
            response.enqueue(new Callback<InfoData>() {
                @Override
                public void onResponse(Call<InfoData> call, Response<InfoData> response) {
                    if(response.isSuccessful()){
                        InfoData infoData = response.body();
                        view.setInfo(infoData);
                        view.toast("Update Successful");
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
    @Override
    public Bitmap imgReSizing(String path){
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

    // 별도로 이미지 올리기( HttpURLConnection )
    private void saveMyImage(Bitmap bitmap, String imagePath){
        new AsyncTask<String, Void, Integer>(){
            String boundary="----------";
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            @Override
            protected Integer doInBackground(String... params) {
                int responeCode = 0;
                try {
                    URL connectUrl = new URL(Networking.getBASE_URL() + "/member/myinfo/");
                    HttpURLConnection conn = (HttpURLConnection) connectUrl.openConnection();
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                    conn.setRequestProperty("Authorization", "Token " + Networking.getToken());
                    conn.setRequestProperty("cache-control", "no-cache");
                    conn.connect();
                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                    ByteArrayOutputStream stream = new ByteArrayOutputStream() ;
                    bitmap.compress( Bitmap.CompressFormat.JPEG, 100, stream) ;
                    byte[] byteArray = stream.toByteArray();
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
                    dos.writeBytes("\r\n--" + boundary + "\r\n");
                    dos.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\"" + imagePath + "\"" + lineEnd);
                    dos.writeBytes("Content-Type: application/octet-stream" + lineEnd);
                    dos.writeBytes(lineEnd);
                    int bytesAvailable = inputStream.available();
                    int maxBufferSize = 1024;
                    int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    byte[] buffer = new byte[bufferSize];
                    int bytesRead = inputStream.read(buffer, 0, bufferSize);
                    Log.d("Test", "image byte is " + bytesRead);
                    // read image
                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = inputStream.available();
                        bufferSize = Math.min(bytesAvailable, maxBufferSize);
                        bytesRead = inputStream.read(buffer, 0, bufferSize);
                    }
                    dos.write(buffer, 0, bufferSize);
                    inputStream.close();

                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    dos.flush();
                    dos.close();
                    if (conn.getResponseCode() == 200 || conn.getResponseCode() == 201) {
                        Log.i(ProfilePictureView.TAG, "conn.getResponseCode(): " + conn.getResponseCode());
                        responeCode = conn.getResponseCode();
                    } else {
                        Log.i(ProfilePictureView.TAG, "conn.getResponseCode(): " + conn.getResponseCode());
                        responeCode = conn.getResponseCode();
                    }
                } catch (Exception e) {
                    Log.e(ProfilePictureView.TAG, e.getMessage());
                }
                return responeCode;
            }

            @Override
            protected void onPostExecute(Integer code) {
                super.onPostExecute(code);

                Log.i(ProfilePictureView.TAG,"onPostExecute");

            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                Log.i(ProfilePictureView.TAG,"onPreExecute");
            }
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }.execute();


    }



}
