package com.hm.project_glue.util.write;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hm.project_glue.R;
import com.hm.project_glue.main.home.data.Response;
import com.hm.project_glue.util.write.photo.GalleryListMain;

import java.util.ArrayList;

import static com.hm.project_glue.main.home.HomeFragment.homeResponses;

public class WriteActivity extends AppCompatActivity implements WritePresenter.View  {
    Button btnWrite, btnWriteBack,btnGroupSelect,btnGallery;
    private final int MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE= 11;
    EditText mEditText;
    HorizontalListView horizontalListView;
    ListView groupListView;
    View groupView;
    Context context;
    PhotosListAdapter listAdapter;
    PopupListAdapter popupAdapter;
    ArrayList<String> photosDatas;
    AlertDialog.Builder groupDialog =null;
    AlertDialog viewgroupDialog;
    int REQ_CODE_IMAGE = 10;
    WritePresenterImpl writePresenter;
    ProgressDialog progress;
    private String selectGroupId ="0";
    private String selectGroupName ="GROUP";
    boolean myPermissions=false;
    public final static String TAG= "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        writePresenter = new WritePresenterImpl(this);
        writePresenter.setView(this);
        photosDatas = new ArrayList<>();
        checkPermissions();

        this.context = this;
        btnWrite        =   (Button)findViewById(R.id.btnWrite);
        btnWriteBack    =   (Button)findViewById(R.id.btnWriteBack);
        btnGroupSelect  =   (Button)findViewById(R.id.btnGroupSelect);
        btnGallery      =   (Button)findViewById(R.id.btnGallery);
        mEditText       =   (EditText) findViewById(R.id.mEditText);
        horizontalListView = (HorizontalListView) findViewById(R.id.writehorizontalListView);
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
        popupAdapter = new PopupListAdapter(this,homeResponses);
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
        if(homeResponses.size() > 0 ){

        }else{
            groupDialog.setTitle("not found Group");
        }
        groupDialog.create();
    }
    private void doTakeAlbum(){

       if(myPermissions){
            Intent i = new Intent(this, GalleryListMain.class);
            startActivityForResult(i, REQ_CODE_IMAGE);
       }else{
           ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                   MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE);
       }
    }


    @Override // 이미지 결과 출력
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode == REQ_CODE_IMAGE && data != null){

            ArrayList<String> result = data.getStringArrayListExtra("pathList");


            if(result.size() > 0){
                showResult(result);
                horizontalListView.setVisibility(View.VISIBLE);
            }else{

                horizontalListView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    myPermissions = true;
                } else {
                    myPermissions = false;
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
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
        btnGroupSelect.setText(groupName);
        viewgroupDialog.dismiss();
    }
    @Override
    public void setGroupListChanged(ArrayList<Response> results) {
//        groupListDatas.addAll(results);
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
    public void checkPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE);
                // MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.

            }
        }
    }
}
