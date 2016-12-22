package com.hm.project_glue.main.info;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hm.project_glue.R;
import com.hm.project_glue.main.MainActivity;
import com.hm.project_glue.main.info.data.InfoData;
import com.hm.project_glue.main.list.data.NotiJson;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class InfoFragment extends Fragment implements InfoPresenter.View {
    private InfoPresenterImpl infoPresenter;
    private EditText etInfoPhone_number, etInfoName, etInfoPassword1, etInfoPassword2, etInfoEmail;
    private LinearLayout imgInfoMyImg,img_InfoPhotoDetail;
    private ImageView info_img,info_detailimg;
    private RelativeLayout PotoDetail,infoLayout;
    private int MY_PERMISSIONS_REQUEST_READ_EXT_STORAGE = 11;
    private int REQ_CODE_IMAGE = 2, LAYOUTSETON = 1, LAYOUTSETOFF = 2;
    private String imgUrl=null;
    private String imgName=null;
    public boolean myPermission=false;
    private Button info_pushApply;
    private Switch info_swNoti_Post, info_swNoti_Comment, info_swNoti_Like;
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
        Button info_btnDefaultimg = (Button) view.findViewById(R.id.info_btnDefaultimg);
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
        imgInfoMyImg        = (LinearLayout) view.findViewById(R.id.info_linearimg);
        img_InfoPhotoDetail = (LinearLayout) view.findViewById(R.id.info_linearPhotoDetail);
        info_img            = (ImageView) view.findViewById(R.id.info_img);
        info_detailimg      = (ImageView) view.findViewById(R.id.info_detailimg);
        info_swNoti_Post    =   (Switch) view.findViewById(R.id.info_swNoti_Post);
        info_swNoti_Comment =   (Switch) view.findViewById(R.id.info_swNoti_Comment);
        info_swNoti_Like    =   (Switch) view.findViewById(R.id.info_swNoti_Like);
        info_pushApply      =   (Button) view.findViewById(R.id.info_pushApply);

        // Push 적용
        info_pushApply.setOnClickListener(v->{
            switchStateCheck();
        });

        // Photo save
        btnPhotoDetailSave.setOnClickListener(v-> {

            bitmap = infoPresenter.getBitmap(info_detailimg);
            infoPresenter.photoUpdate(bitmap, imgName);
            setPhotoLayout(LAYOUTSETON);
        });
        // Default
        info_btnDefaultimg.setOnClickListener(v->{
            imgUrl=null;
            Glide.with(getContext()).load(R.drawable.com_facebook_profile_picture_blank_portrait)
            .into(info_detailimg);
        });

        //close
        btnPhotoDetailClose.setOnClickListener(v-> {
            setPhotoLayout(LAYOUTSETON);
                });
        //Load
        btnPhotoDetailLord.setOnClickListener(v-> {
            // 2016.12.20 galleyActivity 재사용을 위해 int로 구분
            ((MainActivity)getActivity()).galleyActivity(REQ_CODE_IMAGE);
        });


        btnInfoLogOut.setOnClickListener(v -> ((MainActivity)getActivity()).logOut() );
        btnInfoUpdate.setOnClickListener(v -> infoFormCheck() );

        //키보드 off
        infoRelativeLayout.setOnClickListener(v -> {
            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
        });
        // 이미지 클릭
        imgInfoMyImg.setOnClickListener(v ->{
            setPhotoLayout(LAYOUTSETOFF);
            Glide.with(getContext()).load(imgUrl).into(info_img);
        } );

        if( imgUrl==null ){
            Glide.with(getContext()).load(R.drawable.com_facebook_profile_picture_blank_portrait).
                    bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(info_img);
        }else{
            Glide.with(getContext()).load(imgUrl).bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(info_img);
        }
        return view;
    }

    public void switchStateCheck(){
        NotiJson notiJson = new NotiJson(info_swNoti_Post.isChecked(),info_swNoti_Comment.isChecked(),info_swNoti_Like.isChecked());
        infoPresenter.callHttpNoti(notiJson);
    }

    public void setPhotoLayout(int visibleCode){
        Log.i(TAG," setPhotoLayout ");
        ((MainActivity)getActivity()).setTabBar(visibleCode);
        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(1000);

        switch (visibleCode){
            case 1 : // info layout
                if( imgUrl==null ){
                    Glide.with(getContext()).load(R.drawable.com_facebook_profile_picture_blank_portrait).
                            bitmapTransform(new CropCircleTransformation(getContext()))
                            .into(info_img);
                }else{
                    Glide.with(getContext()).load(imgUrl).bitmapTransform(new CropCircleTransformation(getContext()))
                            .into(info_img);
                }
                infoLayout.setVisibility(View.VISIBLE);
                infoLayout.setAnimation(animation);
                PotoDetail.setVisibility(View.GONE);


                break;
            case 2 : // photo layout

                if( imgUrl==null ){
                    Glide.with(getContext()).load(R.drawable.com_facebook_profile_picture_blank_portrait).into(info_detailimg);
                }else{
                    Glide.with(getContext()).load(imgUrl).into(info_detailimg);
                }
                PotoDetail.setVisibility(View.VISIBLE);
                PotoDetail.setAnimation(animation);
                infoLayout.setVisibility(View.GONE);

        }

    }

    public void setBitmap(String imagePath){
        imgUrl = imagePath;
        Glide.with(getContext()).load(imagePath).into(info_detailimg);

    }
    private void infoFormCheck(){
        String phone        = etInfoPhone_number.getText().toString();
        String name         = etInfoName.getText().toString();
        String password1    = etInfoPassword1.getText().toString();
        String password2   = etInfoPassword2.getText().toString();
        String email        = etInfoEmail.getText().toString();
       infoPresenter.infoFormCheck(phone, name, password1, password2, email);
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
        if(infoData.getPost_noti()){
            info_swNoti_Post.setChecked(true);
        }else{
            info_swNoti_Post.setChecked(false);
        }
        if(infoData.getComment_noti()){
            info_swNoti_Comment.setChecked(true);
        }else{
            info_swNoti_Comment.setChecked(false);
        }
        if(infoData.getLike_noti()){
            info_swNoti_Like.setChecked(true);
        }else{
            info_swNoti_Like.setChecked(false);
        }
        if( imgUrl==null){
            Glide.with(getContext()).load(R.drawable.com_facebook_profile_picture_blank_portrait).bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(info_img);
        }else{
            Glide.with(getContext()).load(imgUrl).bitmapTransform(new CropCircleTransformation(getContext()))
                    .into(info_img);
        }
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }



}
