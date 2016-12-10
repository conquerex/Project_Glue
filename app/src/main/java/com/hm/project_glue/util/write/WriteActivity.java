package com.hm.project_glue.util.write;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.hm.project_glue.R;

import java.util.ArrayList;

public class WriteActivity extends AppCompatActivity {
    Button btnWrite, btnWriteBack, btnGallery;
    EditText mEditText;
    ExpandableListView exListView;
    HorizontalListView horizontalListView;
    PhotosListAdapter listAdapter;
    int REQ_CODE_IMAGE = 1;
    ArrayList<String> datas = new ArrayList<>();
    public final static String TAG= "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        btnWrite        =   (Button)findViewById(R.id.btnWrite);
        btnWriteBack    =   (Button)findViewById(R.id.btnWriteBack);
        btnGallery      =   (Button)findViewById(R.id.btnGallery);
        mEditText       =   (EditText) findViewById(R.id.mEditText);
        exListView      =   (ExpandableListView)findViewById(R.id.exListView);

        horizontalListView = (HorizontalListView) findViewById(R.id.horizontalListView);

        btnGallery.setOnClickListener(v ->{
            doTakeAlbum();
        });

        listAdapter = new PhotosListAdapter(
                this, datas, R.layout.write_photos_list_item);

        horizontalListView.setAdapter(listAdapter);

    }

    public void doTakeAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, REQ_CODE_IMAGE);
    }
    public void checkPermissions(){

    }

    @Override // 이미지 결과 출력
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == REQ_CODE_IMAGE && data != null){
            Uri imageUri = data.getData();    // Intent에서 받아온 갤러리 URI
            String selections[] = { MediaStore.Images.Media.DATA}; // 실제 이미지 패스 데이터
            if( Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                Log.i(TAG, "Build.VERSION.SDK_INT < Build.VERSION_CODES.M");
            }
            else {
                checkPermissions();
                Log.i(TAG, "Build.VERSION.SDK_INT > Build.VERSION_CODES.M");
            }
            Cursor cursor = getContentResolver().query(imageUri, selections, null,null,null);

            if(cursor.moveToNext()){

                String imagePath = cursor.getString(0);
                Log.i("imagePath : ", imagePath);
                datas.add(imagePath);

            }
            if(datas.size() > 0){
                horizontalListView.setVisibility(View.VISIBLE);
            }else{
                horizontalListView.setVisibility(View.GONE);
            }
            listAdapter.notifyDataSetChanged();
        }
    }


}
