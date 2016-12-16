package com.hm.project_glue.main.info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.hm.project_glue.R;
import com.hm.project_glue.main.MainActivity;
import com.hm.project_glue.main.info.Data.InfoData;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.facebook.GraphRequest.TAG;

public class InfoFragment extends Fragment implements InfoPresenter.View {
    private InfoPresenterImpl infoPresenter;
    private EditText etInfoPhone_number, etInfoName, etInfoPassword1, etInfoPassword2, etInfoEmail;
    private ImageView imgInfoMyImg;
    private Button btnInfoLogOut, btnInfoUpdate;
    private RelativeLayout infoRelativeLayout;
    private String imgUrl="";
    InputMethodManager imm;
    public InfoFragment() {

    }

    public static InfoFragment newInstance() {
        InfoFragment fragment = new InfoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        infoPresenter = new InfoPresenterImpl(this);
        infoPresenter.setView(this);


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info, container, false);
        btnInfoLogOut = (Button)view.findViewById(R.id.btnLogOut);
        btnInfoLogOut.setOnClickListener(v ->
            ((MainActivity)getActivity()).logOut()
        );
        btnInfoUpdate = (Button)view.findViewById(R.id.btnInfoUpdate);
        btnInfoUpdate.setOnClickListener(v -> infoFormCheck());

        infoRelativeLayout = (RelativeLayout) view.findViewById(R.id.infoRelativeLayout);
        etInfoPhone_number  = (EditText) view.findViewById(R.id.etInfoPhone_number);
        etInfoName          = (EditText) view.findViewById(R.id.etInfoName);
        etInfoPassword1     = (EditText) view.findViewById(R.id.etInfoPassword1);
        etInfoPassword2     = (EditText) view.findViewById(R.id.etInfoPassword2);
        etInfoEmail         = (EditText) view.findViewById(R.id.etInfoEmail);
        imgInfoMyImg         = (ImageView) view.findViewById(R.id.imgInfoMyImg);
        imgInfoMyImg.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PhotoDetailActivity.class);
            if(!imgUrl.equals("")){
                intent.putExtra("imagePath",imgUrl);
                Log.i(TAG, "send imgURL");
            }
            startActivityForResult(intent, 1);
        });
        imm = (InputMethodManager)getContext().getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etInfoPhone_number.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(etInfoName.getWindowToken(),0);
        imm.hideSoftInputFromWindow(etInfoPassword1.getWindowToken(),0);
        imm.hideSoftInputFromWindow(etInfoPassword2.getWindowToken(),0);
        imm.hideSoftInputFromWindow(etInfoEmail.getWindowToken(),0);
        infoRelativeLayout.setOnClickListener(v -> { // keyboard
            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });

        if( imgUrl.equals("") ){
            Glide.with(getContext()).load(R.drawable.com_facebook_profile_picture_blank_portrait).bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(imgInfoMyImg);
        }else{
            Glide.with(getContext()).load(imgUrl).bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(imgInfoMyImg);
        }

        return view;
    }
    public void setBitmap(String imagePath){
        imgUrl = imagePath;
        Glide.with(getContext()).load(imagePath).bitmapTransform(new CropCircleTransformation(getContext()))
        .into(imgInfoMyImg);

    }
    private void infoFormCheck(){
        String phone        = etInfoPhone_number.getText().toString();
        String name         = etInfoName.getText().toString();
        String password1    = etInfoPassword1.getText().toString();
        String password2   = etInfoPassword2.getText().toString();
        String email        = etInfoEmail.getText().toString();



       infoPresenter.infoFormCheck(phone, name, password1, password2, email, imgUrl);

    }
    @Override
    public void setInfo(InfoData infoData){
        if(infoData.getPhone_number() != null){
            etInfoPhone_number.setText(infoData.getPhone_number());
        }
        if(infoData.getName() != null){
            etInfoName.setText(infoData.getName());
        }
        if(infoData.getEmail() != null){
            etInfoEmail.setText(infoData.getEmail());
        }
        if(infoData.getImage() != null ){
            this.imgUrl = infoData.getImage();
        }
        if( imgUrl.equals("") ){
            Glide.with(getContext()).load(R.drawable.com_facebook_profile_picture_blank_portrait).bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(imgInfoMyImg);
        }else{
            Glide.with(getContext()).load(imgUrl).bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(imgInfoMyImg);
        }

    }

}
