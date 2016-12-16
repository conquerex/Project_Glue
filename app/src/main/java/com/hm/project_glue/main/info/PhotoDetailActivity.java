package com.hm.project_glue.main.info;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.hm.project_glue.R;
import com.hm.project_glue.util.Networking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.hm.project_glue.R.id.btnPhotoDetailClose;

public class PhotoDetailActivity extends AppCompatActivity implements View.OnClickListener{
    private int REQ_CODE_IMAGE = 1;
    private String TAG = "TEST";
    private ImageView imgView;
    private Bitmap bitmap;
    private String imagePath="";
    private String getPath="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        imgView = (ImageView) findViewById(R.id.imgDetail);
        if(getIntent().getStringExtra("imagePath") != null){
            getPath=getIntent().getStringExtra("imagePath");
            imgView.setImageBitmap(imgReSizing(getPath));
        }else{
            Log.i(TAG, "getStringExtra null..");
        }

        Button btnPhotoDetailClose = (Button) findViewById(R.id.btnPhotoDetailClose);
        Button btnPhotoDetailSave = (Button) findViewById(R.id.btnPhotoDetailSave);
        Button btnPhotoDetailLord = (Button) findViewById(R.id.btnPhotoDetailLord);
        btnPhotoDetailClose.setOnClickListener(this);
        btnPhotoDetailSave.setOnClickListener(this);
        btnPhotoDetailLord.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgDetail :
                Toast.makeText(this, "img", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnPhotoDetailLord :
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQ_CODE_IMAGE);
                break;
            case btnPhotoDetailClose :
                this.finish();
                break;
            case R.id.btnPhotoDetailSave :
                    bitmapCheck();
                break;

        }
    }
    private Bitmap imgReSizing(String path){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        int reqWidth = dm.widthPixels;
        int reqHeight = dm.heightPixels;
        imagePath = path;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

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
                        Log.i(TAG, "conn.getResponseCode(): " + conn.getResponseCode());
                        responeCode = conn.getResponseCode();
                    } else {
                        Log.i(TAG, "conn.getResponseCode(): " + conn.getResponseCode());
                        responeCode = conn.getResponseCode();
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
                return responeCode;
            }

            @Override
            protected void onPostExecute(Integer code) {
                super.onPostExecute(code);

                Log.i(TAG,"onPostExecute");

            }
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                Log.i(TAG,"onPreExecute");
            }
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }.execute();


    }
    private void bitmapCheck(){

        if(!imagePath.equals(getPath) ) { // 기존 이미지와 같지 않으면

//            saveMyImage(bitmap, imagePath);
            Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
            Intent backIntent = new Intent();
            backIntent.putExtra("imagePath", imagePath);
            setResult(2, backIntent);
            finish();
        }else{
            Toast.makeText(this, "No selected image", Toast.LENGTH_SHORT).show();
        }

    }
    @Override // 이미지 결과 출력
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_IMAGE && data != null) {
            Uri imageUri = data.getData();    // Intent에서 받아온 갤러리 URI
            String selections[] = {MediaStore.Images.Media.DATA}; // 실제 이미지 패스 데이터
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            } else {
//                checkPermissions();
            }
            Cursor cursor = getContentResolver().query(imageUri, selections, null, null, null);

             // 이미지 사이즈 처리
            if (cursor.moveToNext()) {

                imgView.setImageBitmap(imgReSizing(cursor.getString(0)));
            }

        }
    }



}
