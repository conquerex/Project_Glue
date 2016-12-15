package com.hm.project_glue.main.info;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import java.io.FileInputStream;

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
            imgReSizing(getPath);
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
    private void imgReSizing(String path){
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
//                final int halfHeight = height / 2;
//                final int halfWidth = width / 2;
//                while ((halfHeight / inSampleSize) >= reqHeight
//                        && (halfWidth / inSampleSize) >= reqWidth) {
//                    inSampleSize *= 2;
//                    Log.i( TAG, "While : inSampleSize:"+inSampleSize);
//                }
                options.inSampleSize = (width / reqWidth);
            }
            Log.i( TAG, "inSampleSize:"+inSampleSize);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(imagePath, options);
            imgView.setImageBitmap(bitmap);

        }catch (Exception e ){
            Log.e(TAG, e.getMessage());
        }


    }
    private void saveMyImage(Bitmap bitmap){




    }
    private void bitmapCheck(){

        if(!imagePath.equals(getPath) ) { // 기존 이미지와 같지 않으면

            saveMyImage(bitmap);
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
                imgReSizing(cursor.getString(0));
            }

        }
    }



}
