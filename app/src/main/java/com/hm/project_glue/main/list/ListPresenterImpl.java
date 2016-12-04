package com.hm.project_glue.main.list;

import android.content.Context;
import android.util.Log;

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

    }

    @Override
    public void setView(View view) {
        this.view = view;
    }


    public void callHttp(String group){

        final Call<PostData> postData = ListRestAdapter.getInstance().getData(group);
        postData.enqueue(new Callback<PostData>() {
            @Override
            public void onResponse(Call<PostData> call, Response<PostData> response) {
                if(response.isSuccessful()) {
                    PostData data = response.body();
                    List<PostData.Photos> list = data.getPhotos();

                    for(PostData row : rows ){
                        Log.i("result : ", row.getROAD_NM());
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

}
