package com.hm.project_glue.util.addGroup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hm.project_glue.R;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class AddGroupActivity extends AppCompatActivity implements AddGroupPresenter.View {
    private static final String TAG = "AddGroupActivity";
    private Bitmap bitmap;
    private String imgUrl;
    private AddGroupPresenterImpl addGroupPresenter;
    ProgressDialog progress;
    Context context;

    Button btnAddGroupBack;
    Button btnAddGroupSave;
    ImageView ivAddGroupGallery;
    EditText etAddGroupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
        addGroupPresenter = new AddGroupPresenterImpl(this);
        addGroupPresenter.setView(this);
        this.context = this;


        ivAddGroupGallery = (ImageView)findViewById(R.id.ivAddGroupGallery);
        etAddGroupName    = (EditText)findViewById(R.id.etAddGroupName);
        btnAddGroupBack   = (Button)findViewById(R.id.btnAddGroupBack);
        btnAddGroupSave   = (Button)findViewById(R.id.btnAddGroupSave);

        // 이미지 클릭시
        ivAddGroupGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                Log.i(TAG, "----- setOnClickListener" );
                startActivityForResult(intent, 3);
            }
        });

        // 뒤로가기 버튼 클릭시
        btnAddGroupBack.setOnClickListener(v ->
                this.finish()
        );

        // 저장 버튼 클릭시
        btnAddGroupSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(context).load(imgUrl).bitmapTransform(new CropCircleTransformation(context))
                        .into(ivAddGroupGallery);
                addGroupPresenter.photoAddGroupUpdate(bitmap);
                finish();
            }
        });
    }

    public void setAddGroupBitmap(String imagePath){
        imgUrl = imagePath;
        Log.i(TAG, "----- imagePath:" + imagePath );
        bitmap = addGroupPresenter.imgAddGroupReSizing(imagePath);
        ivAddGroupGallery.setImageBitmap(bitmap);
    }

    @Override
    public void progressAddGroupShow(boolean status) {
        if(status){
            progress = new ProgressDialog(context);
            progress.setMessage("Upload....");
            progress.setProgressStyle((ProgressDialog.STYLE_SPINNER));
            progress.setCancelable(false);
            progress.show();
        }else{
            if(progress != null) {
                progress.dismiss();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "------ onActivityResult");
//        Bundle extras = data.getExtras();
//        if (extras != null) {
//            Bitmap photo = extras.getParcelable("data");
//            ivAddGroupGallery.setImageBitmap(photo);
//        }
        if(data != null){
            Uri imageUri = data.getData();    // Intent에서 받아온 갤러리 URI
            String selections[] = { MediaStore.Images.Media.DATA}; // 실제 이미지 패스 데이터
            Cursor cursor = getContentResolver().query(imageUri, selections, null,null,null);
            if(cursor.moveToNext()){
                String imagePath = cursor.getString(0);  // 사이즈 지정 옵션
                setAddGroupBitmap(imagePath);
                Log.i(TAG, "------ addGroup.setAddGroupBitmap");
            }
        }
    }

    @Override
    public void addGroupResult(int code) {
        Log.i(TAG, "----------- Add Group Result ----- "+code);
        switch (code){
            case 200 :
                Toast.makeText(context,"Add Group Successful",Toast.LENGTH_LONG).show();
                this.finish();
                break;
            case 201 :
                Toast.makeText(context,"Add Group Successful",Toast.LENGTH_LONG).show();
                this.finish();
                break;
            case 400 :
                Toast.makeText(context,"Add Group Fail 400",Toast.LENGTH_LONG).show();
                break;
            case 403 :
                Toast.makeText(context,"page permission denied",Toast.LENGTH_LONG).show();
                break;
            case 404 :
                Toast.makeText(context,"page not found 404",Toast.LENGTH_LONG).show();
                break;
            case 500 :
                Toast.makeText(context,"Server Error 500",Toast.LENGTH_LONG).show();
                break;
            default: Toast.makeText(context,"Add Group Fail",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
