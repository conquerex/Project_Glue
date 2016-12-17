package com.hm.project_glue.main.info;

import android.content.Intent;
import android.graphics.Bitmap;
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


public class InfoFragment extends Fragment implements InfoPresenter.View {
    private InfoPresenterImpl infoPresenter;
    private EditText etInfoPhone_number, etInfoName, etInfoPassword1, etInfoPassword2, etInfoEmail;
    private ImageView imgInfoMyImg,img_InfoPhotoDetail;
    private RelativeLayout PotoDetail,infoLayout;
    private int REQ_CODE_IMAGE = 2;
    private String imgUrl="";
    Bitmap bitmap;
    String TAG = "TEST";

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

        Button btnInfoLogOut = (Button) view.findViewById(R.id.btnLogOut);
        Button btnInfoUpdate = (Button) view.findViewById(R.id.btnInfoUpdate);
        Button btnPhotoDetailLord = (Button) view.findViewById(R.id.btnPhotoDetailLord);
        Button btnPhotoDetailClose = (Button) view.findViewById(R.id.btnPhotoDetailClose);
        Button btnPhotoDetailSave = (Button) view.findViewById(R.id.btnPhotoDetailSave);
        RelativeLayout infoRelativeLayout = (RelativeLayout) view.findViewById(R.id.infoRelativeLayout);
        infoLayout          = (RelativeLayout) view.findViewById(R.id.infoLayout);
        PotoDetail          = (RelativeLayout) view.findViewById(R.id.info_photodetail);
        etInfoPhone_number  = (EditText) view.findViewById(R.id.etInfoPhone_number);
        etInfoName          = (EditText) view.findViewById(R.id.etInfoName);
        etInfoPassword1     = (EditText) view.findViewById(R.id.etInfoPassword1);
        etInfoPassword2     = (EditText) view.findViewById(R.id.etInfoPassword2);
        etInfoEmail         = (EditText) view.findViewById(R.id.etInfoEmail);
        imgInfoMyImg        = (ImageView) view.findViewById(R.id.imgInfoMyImg);
        img_InfoPhotoDetail = (ImageView) view.findViewById(R.id.img_InfoPhotoDetail);
        btnPhotoDetailSave.setOnClickListener(v-> { // Photo save
            Glide.with(getContext()).load(imgUrl).bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(imgInfoMyImg);
            setPhotoLayout(1);
        });

        btnPhotoDetailClose.setOnClickListener(v-> setPhotoLayout(1)  );
        btnInfoLogOut.setOnClickListener(v -> ((MainActivity)getActivity()).logOut() );
        btnInfoUpdate.setOnClickListener(v -> infoFormCheck() );
        btnPhotoDetailLord.setOnClickListener(v-> {
            ((MainActivity)getActivity()).galleyActivity();
        });
        infoRelativeLayout.setOnClickListener(v -> {
//            imm = (InputMethodManager)getApplicationContext().getSystemService(INPUT_METHOD_SERVICE);
//            imm.hideSoftInputFromWindow(etInfoPhone_number.getWindowToken(), 0);
//            imm.hideSoftInputFromWindow(etInfoName.getWindowToken(),0);
//            imm.hideSoftInputFromWindow(etInfoPassword1.getWindowToken(),0);
//            imm.hideSoftInputFromWindow(etInfoPassword2.getWindowToken(),0);
//            imm.hideSoftInputFromWindow(etInfoEmail.getWindowToken(),0);
            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });
        imgInfoMyImg.setOnClickListener(v ->{
            setPhotoLayout(2);
        } );


        if( imgUrl.equals("") ){
            Glide.with(getContext()).load(R.drawable.com_facebook_profile_picture_blank_portrait).bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(imgInfoMyImg);
        }else{
            Glide.with(getContext()).load(imgUrl).bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(imgInfoMyImg);
        }

        return view;
    }
    public void setPhotoLayout(int visibleCode){
        Log.i(TAG," setPhotoLayout ");
        ((MainActivity)getActivity()).setTabBar(visibleCode);
        switch (visibleCode){
            case 2 : // photolayout
                infoLayout.setVisibility(View.GONE);
                PotoDetail.setVisibility(View.VISIBLE);
                Log.i(TAG," setPhotoLayout  2");
                break;
            case 1 : // inflayout
                infoLayout.setVisibility(View.VISIBLE);
                PotoDetail.setVisibility(View.GONE);
        }

    }

    public void setBitmap(String imagePath){
        imgUrl = imagePath;
        Log.i(TAG, "imagePath:"+imagePath );
        bitmap = infoPresenter.imgReSizing(imagePath);
        img_InfoPhotoDetail.setImageBitmap(bitmap);

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
