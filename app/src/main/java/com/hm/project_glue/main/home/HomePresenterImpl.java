package com.hm.project_glue.main.home;

import android.content.Context;
import android.util.Log;

import com.hm.project_glue.Util.Networking;
import com.hm.project_glue.main.home.data.GroupListData;
import com.hm.project_glue.main.list.ListRestAdapter;
import com.hm.project_glue.main.list.data.PostData;
import com.hm.project_glue.main.list.data.Results;

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

        // fragment.callHttp();
    }

    @Override
    public void setView(View view) {

    }

    // 2016.12.06
    @Override
    public void callHttp() {
        ArrayList<Results> post = new ArrayList<>();
        // Token 가져오기
        String token = Networking.getToken();
        Log.i(TAG, "----------- token ---- "+ token);

        final Call<PostData> postData = ListRestAdapter.getInstance().getData();
        // final Call<GroupListData> dataCall =


        postData.enqueue(new Callback<PostData>() {
            @Override
            public void onResponse(Call<PostData> call, Response<PostData> response) {

                if(response.isSuccessful()) {
//                    RemoteData data = response.body();
//
//                    List<RemoteData.Row> rows = data.getSeoulRoadNameInfo().getRow();
//
//                    for(RemoteData.Row row : rows ){
//                        Log.i("result : ", row.getROAD_NM());
//                    }
                    PostData body =  response.body();
                    List<Results> ListResults = body.getResults();

                    for(Results result : ListResults){
                        Results data = new Results();
                        result.getContent();
                        Log.i(TAG, result.toString());
                        post.add(data);
                    }

                }else{
                    Log.e("ERROR : ",response.message());
                }
            }

            @Override
            public void onFailure(Call<PostData> call, Throwable t) {

            }

        });
    }
}
