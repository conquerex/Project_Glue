package com.hm.project_glue.main.home;

import android.content.Context;
import android.util.Log;

import com.hm.project_glue.util.Networking;
import com.hm.project_glue.main.home.data.HomeData;
import com.hm.project_glue.main.home.data.HomeGroupData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HM on 2016-11-29.
 */

public class HomePresenterImpl implements HomePresenter {
    private static final String TAG = "HomePresenter";
    private HomeFragment fragment;
    private HomeModel model;
    private Context context;

    // 2016.12.06
    public HomePresenterImpl(HomeFragment fragment) {
        this.fragment = fragment;
        model = new HomeModel(fragment.getContext());
        context = fragment.getActivity();

        callHttp();
    }

    @Override
    public void setView(View view) {

    }

    // 2016.12.06
    @Override
    public void callHttp() {
        ArrayList<HomeData> data = new ArrayList<>();
        // 로그인 유지를 위해 Token 가져오기
        String token = Networking.getToken();
        Log.i(TAG, "----------- token ---- "+ token);

        // Adapter를 통해 인터페이스의 getData 호출
        final Call<HomeGroupData> dataCall = HomeRestAdapter.getInstance().getData();

        // 인터페이스로부터 받아온 Call 객체는 한 번만 사용할 수 있으므로 싱글 응답/요청 쌍이 됨
        // 비동기식으로 사용 : enqueue
        dataCall.enqueue(new Callback<HomeGroupData>() {
            @Override
            public void onResponse(Call<HomeGroupData> call, Response<HomeGroupData> response) {

                if(response.isSuccessful()) {
                    HomeGroupData body =  response.body();
                    List<HomeData> homeResults = body.getHomeDatas();

                    for(HomeData homeData : homeResults){
                        /*
                        작성 예정
                         */
                    }
                }else{
                    Log.e("ERROR : ",response.message());
                }
            }

            @Override
            public void onFailure(Call<HomeGroupData> call, Throwable t) {
                Log.i(TAG, "----------- t.getMessage() ---- "+ t.getMessage());
            }
        });
    }
}
