package com.hm.project_glue.util.addGroup;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hm.project_glue.R;

public class AddGroupActivity extends AppCompatActivity implements AddGroupPresenter.View {
    private static final String TAG = "AddGroupActivity";
    ProgressDialog progress;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group);
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
