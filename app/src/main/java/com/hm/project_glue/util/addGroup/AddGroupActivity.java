package com.hm.project_glue.util.addGroup;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hm.project_glue.R;
import com.hm.project_glue.main.MainActivity;

public class AddGroupActivity extends AppCompatActivity implements AddGroupPresenter.View {
    private static final String TAG = "AddGroupActivity";
    private Bitmap bitmap;
    private String imgUrl;
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

        ivAddGroupGallery = (ImageView)findViewById(R.id.ivAddGroupGallery);
        etAddGroupName    = (EditText)findViewById(R.id.etAddGroupName);
        btnAddGroupBack   = (Button)findViewById(R.id.btnAddGroupBack);
        btnAddGroupSave   = (Button)findViewById(R.id.btnAddGroupSave);

        ivAddGroupGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)MainActivity.mainContext).galleyActivity(2);
            }
        });

        btnAddGroupBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnAddGroupSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void setAddGroupBitmap(String imagePath){
        imgUrl = imagePath;
        Log.i(TAG, "imagePath:"+imagePath );
//        bitmap = infoPresenter.imgReSizing(imagePath);
//        ivAddGroupGallery.setImageBitmap(bitmap);
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
