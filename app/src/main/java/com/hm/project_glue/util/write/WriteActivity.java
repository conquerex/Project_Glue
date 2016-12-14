package com.hm.project_glue.util.write;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.hm.project_glue.R;
import com.hm.project_glue.util.write.data.Response;
import com.hm.project_glue.util.write.photo.GalleryListMain;

import java.util.ArrayList;

public class WriteActivity extends AppCompatActivity implements WritePresenter.View  {
    Button btnWrite, btnWriteBack,btnGroupSelect;
    ImageButton btnGallery;
    EditText mEditText;
    HorizontalListView horizontalListView;
    ListView groupListView;
    View groupView;
    Context context;
    PhotosListAdapter listAdapter;
    PopupListAdapter popupAdapter;
    ArrayList<String> photosDatas;
    ArrayList<Response> groupListDatas;
    AlertDialog.Builder groupDialog =null;
    AlertDialog viewgroupDialog;
    int REQ_CODE_IMAGE = 1;
    WritePresenterImpl writePresenter;
    ProgressDialog progress;
    private String selectGroupId ="0";
    private String selectGroupName ="GROUP";
    public final static String TAG= "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        writePresenter = new WritePresenterImpl(this);
        writePresenter.setView(this);
        photosDatas = new ArrayList<>();
        groupListDatas = new ArrayList<>();
        this.context = this;
        btnWrite        =   (Button)findViewById(R.id.btnWrite);
        btnWriteBack    =   (Button)findViewById(R.id.btnWriteBack);
        btnGroupSelect  =   (Button)findViewById(R.id.btnGroupSelect);
        btnGallery      =   (ImageButton)findViewById(R.id.btnGallery);
        mEditText       =   (EditText) findViewById(R.id.mEditText);
        horizontalListView = (HorizontalListView) findViewById(R.id.horizontalListView);
        //TODO 완성되면 주석 풀기
//        selectGroupId = getGroupId();
//        selectGroupName = getGroupName();
        btnGallery.setOnClickListener(v ->{
            doTakeAlbum();
        });
        btnGroupSelect.setOnClickListener(v ->{
            setGroupDialog();

            viewgroupDialog = groupDialog.show();
        });
        btnWrite.setOnClickListener(v -> {
            //TODO
            // 사진 체크, 값 보내기
            String content = mEditText.getText().toString();

            if(content.equals("") || selectGroupId.equals("0")){
                Toast.makeText(this,"input content or Select Group",Toast.LENGTH_SHORT).show();
            }else {

                writePresenter.httpPosting(photosDatas, selectGroupId, content);
            }
        });
        btnWriteBack.setOnClickListener(v -> {
            this.finish();
        });
        popupAdapter = new PopupListAdapter(this, groupListDatas);
        listAdapter = new PhotosListAdapter(
                this, photosDatas, R.layout.write_photos_list_item);
        horizontalListView.setAdapter(listAdapter);
    }
    private void  setGroupDialog(){
        groupView = getLayoutInflater().inflate(R.layout.popupgroup, null);
        groupListView = (ListView)groupView.findViewById(R.id.popupListView);
        groupListView.setAdapter(popupAdapter);
        groupDialog = new AlertDialog.Builder(this);
        groupDialog.setView(groupView);
        if(groupListDatas.size() > 0 ){

        }else{
            groupDialog.setTitle("not found Group");
        }
        groupDialog.create();
    }
    private void doTakeAlbum(){
        Intent i = new Intent(this, GalleryListMain.class);
        startActivityForResult(i, REQ_CODE_IMAGE);
    }
    public void checkPermissions(){
        //TODO

    }

    @Override // 이미지 결과 출력
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == REQ_CODE_IMAGE && data != null){

            ArrayList<String> result = data.getStringArrayListExtra("pathList");

            if( Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
                Log.i(TAG, "Build.VERSION.SDK_INT < Build.VERSION_CODES.M");
            }
            else {
                checkPermissions();
                Log.i(TAG, "Build.VERSION.SDK_INT > Build.VERSION_CODES.M");
            }

            if(result.size() > 0){
                showResult(result);
                horizontalListView.setVisibility(View.VISIBLE);
            }else{

                horizontalListView.setVisibility(View.GONE);
            }

        }
    }
    private void showResult(ArrayList<String> paths){
        if(photosDatas == null){
            photosDatas = new ArrayList<>();
        }
        photosDatas.clear();
        photosDatas.addAll(paths);

        if(listAdapter == null){
            listAdapter  = new PhotosListAdapter(
                    this, photosDatas, R.layout.write_photos_list_item);
        }else {
            listAdapter.setPathList(photosDatas);
            listAdapter.notifyDataSetChanged();
            Log.i(TAG, "listAdapter.notifyDataSetChanged();");
        }
    }
    @Override
    public void groupChanged(String groupId, String groupName ) {
        btnGroupSelect.setText(groupName);
        selectGroupId = groupId;
        selectGroupName = groupName;
        viewgroupDialog.dismiss();
    }
    @Override
    public void setGroupListChanged(ArrayList<Response> results) {
        groupListDatas.addAll(results);
        popupAdapter.notifyDataSetChanged();
    }

    @Override
    public void progressShow(boolean status) {
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
    public void writeResult(int code){
        Log.i(TAG, "writeResult:"+code);
        switch (code){
            case 200 :
                Toast.makeText(context,"Write Successful",Toast.LENGTH_LONG).show();
                this.finish();
                break;
            case 201 :
                Toast.makeText(context,"Write Successful",Toast.LENGTH_LONG).show();
                this.finish();
                break;
            case 400 :
                Toast.makeText(context,"Write Fail 400",Toast.LENGTH_LONG).show();
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

            default: Toast.makeText(context,"Write Fail",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
