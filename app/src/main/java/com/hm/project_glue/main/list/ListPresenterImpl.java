package com.hm.project_glue.main.list;

import android.content.Context;
import android.util.Log;

import com.hm.project_glue.Util.Networking;
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
//conn.setRequestProperty("Value", "test");
public class ListPresenterImpl implements ListPresenter {
    private ListFragment fragment;
    private ListModel listInModel;
    private ListPresenter.View view;
    private static final String TAG = "TEST";

    Context context;
    public ListPresenterImpl(ListFragment fragment) {
        this.fragment=fragment;
        listInModel = new ListModel(fragment.getContext());
        context = fragment.getActivity();


        callHttp();
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void getPostJson(String GroupId) {

    }


    public void callHttp(){
        ArrayList<Results> post = new ArrayList<>();
        String token = Networking.getToken();
        Log.i(TAG, token);
        String group="3";
        final Call<PostData> postData = ListRestAdapter.getInstance().getData();
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
