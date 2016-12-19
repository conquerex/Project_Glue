package com.hm.project_glue.main.info;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.facebook.login.widget.ProfilePictureView;
import com.hm.project_glue.main.info.Data.InfoData;
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
    public void photoUpdate(Bitmap bitmap){
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
                        view.toast("upload");
                    }
                    Log.i(TAG, "myInfoData response Code : "+response.code());
                }
                @Override
                public void onFailure(Call<InfoData> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });
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
    private void saveMyImage(Bitmap bitmap, String imagePath){
        new AsyncTask<String, Void, Integer>(){
            String boundary="----------";
            String lineEnd = "\r\n";
            String twoHyphens = "--";

            int responseCode = 0;

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
