package com.hm.project_glue.main.list;

import android.content.Context;
import android.util.Log;

import com.hm.project_glue.main.list.data.Photo;
import com.hm.project_glue.main.list.data.PostData;
import com.hm.project_glue.main.list.data.Results;
import com.hm.project_glue.util.Networking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public ArrayList<Results> callHttp(String GroupId){

        Map<String, String> params = new HashMap<>();
        params.put("page", "2");
        ArrayList<Results> post = new ArrayList<>();
        String authorization = "Token "+ Networking.getToken();
        final Call<PostData> postData = ListRestAdapter.getInstance().getListData(authorization, GroupId, params);
        postData.enqueue(new Callback<PostData>() {
            @Override
            public void onResponse(Call<PostData> call, Response<PostData> response) {

                if(response.isSuccessful()){
                    PostData body = response.body();
                    List<Results> listResults = body.getResults();

                    for (Results result : listResults) {
                        Results data = new Results();
                        data.setContent(result.getContent());
                        data.setUploaded_user(result.getUploaded_user());
                        data.setLikes_count(result.getLikes_count());
                        data.setGroup(result.getGroup());
                        data.setPhotos(result.getPhotos());



                        for (Photo photo_URL : result.getPhotos().getPhotos()) {
                            Photo photo = new Photo();
                            photo.setFull_size(photo_URL.getFull_size());
                            Log.i(TAG, "setFull_size:"+photo.getFull_size());
                            data.getPhotos().getPhotos().add(photo);
                        }

                        post.add(data);
                    }

                    Log.i(TAG, "onResponse"+response.body().toString());
                    view.dataChanged(post);



                }
                else{
                    Log.e("ERROR : ",response.message());
                }

            }

            @Override
            public void onFailure(Call<PostData> call, Throwable t) {

            }

        });
        Log.i(TAG,"callHttp : "+post.toString());
        return post;
    }





}
