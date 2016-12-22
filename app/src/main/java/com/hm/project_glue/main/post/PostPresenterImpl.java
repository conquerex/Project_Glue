package com.hm.project_glue.main.post;


import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.hm.project_glue.main.list.data.Comments;
import com.hm.project_glue.main.list.data.Posts;
import com.hm.project_glue.util.Networking;
import com.hm.project_glue.util.http.ListRestAdapter;

import retrofit2.Call;

/**
 * Created by HM on 2016-12-22.
 */

public class PostPresenterImpl implements Presenter{
    Presenter.View view;
    PostPresenterImpl(Fragment fragment){

    }
    public void callHttpPostDetail(String postId){
        String authorization = "Token " + Networking.getToken();
        new AsyncTask<String, Void, Posts>() {
            Posts post;
            protected Posts doInBackground(String... params) {
                try {
                    final Call<Posts> response = ListRestAdapter.getInstance().postDetailData(authorization, postId);
                    post = response.execute().body();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return post;
            }
            protected void onPreExecute() {
                super.onPreExecute();
            }
            protected void onPostExecute(Posts  res) {
                super.onPostExecute(res);
                view.dataSet(res);
            }
        }.execute();
    }
    public void callHttpCommentCreate(String postId, String comment){
        String authorization = "Token " + Networking.getToken();
        new AsyncTask<String, Void, Comments>() {
            Comments res;
            protected Comments doInBackground(String... params) {
                try {
//                    Map<String, RequestBody> bodyMap = new HashMap<>();
//                    bodyMap.put("content", RequestBody.create(MediaType.parse("form-data"), comment));
                    final Call<Comments> response = ListRestAdapter.getInstance().commentCreateData(authorization, postId, comment);
                    res = response.execute().body();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return res;
            }
            protected void onPreExecute() {
                super.onPreExecute();
            }
            protected void onPostExecute(Comments  res) {
                super.onPostExecute(res);
                view.commentChanged();
            }
        }.execute();
    }


    @Override
    public void setView(View view) {
        this.view = view;
    }
}
