package com.hm.project_glue.main.info;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.hm.project_glue.main.info.Data.InfoData;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.util.http.ListRestAdapter;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by HM on 2016-11-29.
 */

public class InfoPresenterImpl implements InfoPresenter {

    private InfoPresenter.View view;
    private Context context;
    private InfoModel infoModel;
    private static String userId ="17"; // 임시 아이디 폰번호 ( 12121212 )
//    private InfoData infoData;
    public InfoPresenterImpl(InfoFragment fragment){
        this.context = fragment.getContext();
        infoModel = new InfoModel(fragment.getContext());
//        infoData = new InfoData();
        getUserInfo();
    }
    @Override
    public void setView(View view) { this.view = view; }


    @Override
    public void infoFormCheck(String phone, String name, String password1, String password2, String email){
        String msg="";
        if(phone.length() < 8){
            msg = "Phone Number Minimum lenth 8";
        }else{
            if(name.length() < 5){
                msg = "Name Minimum lenth 8";
            }else{
                if(password1.length() < 13){
                    msg = "password Minimum lenth 13";
                }else{
                    if(!password1.equals(password2)){
                        msg = "password not same";
                    }else{
                        if(!isEmailPattern(email)){
                            msg = "email address mismatch";
                        }else{
                            // 모두 검증 통과
                            infoUpdate(phone, name, password1, email);
                            return;
                        }
                    }
                }
            }
        }
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
    private boolean isEmailPattern(String email){
        String pattern = "[\\w\\~\\-\\.]+@[\\w\\~\\-]+(\\.[\\w\\~\\-]+)+";
        return Pattern.matches(pattern, email.trim());
    }
    private void infoUpdate(String phone, String name, String password,  String email){
        Log.i(TAG, "infoUpdate");
    }

    private void getUserInfo(){
        try {
            String authorization = "Token "+ Networking.getToken();
            final Call<InfoData> response = ListRestAdapter.getInstance().myInfoData(authorization);
            response.enqueue(new Callback<InfoData>() {
                @Override
                public void onResponse(Call<InfoData> call, Response<InfoData> response) {
                    if(response.isSuccessful()){
                        InfoData infoData = response.body();
                        view.setInfo(infoData);
                    }
                    Log.i(TAG, "myInfoData response Code : "+response.code());
                }
                @Override
                public void onFailure(Call<InfoData> call, Throwable t) {
                   Log.e(TAG, t.getMessage());
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }






}
