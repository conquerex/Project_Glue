package com.hm.project_glue.util.http;

import android.os.AsyncTask;
import android.widget.TextView;

import com.hm.project_glue.main.list.data.Dislike;
import com.hm.project_glue.main.list.data.Like;
import com.hm.project_glue.util.Networking;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.hm.project_glue.main.timeline.TimelineFragment.likeChanged;

/**
 * Created by HM on 2016-12-19.
 */

public class CallRest {


    public static void callHttpLogout(){
        String authorization = "Token " + Networking.getToken();
        new AsyncTask<String, Void, String>() {
            protected String doInBackground(String... params) {
                try {
                    final Call<String> response = ListRestAdapter.getInstance().logOutData(authorization);
                    response.execute().body();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            }
            protected void onPreExecute() {
                super.onPreExecute();
            }
            protected void onPostExecute(String  str) {
                super.onPostExecute(str);
            }
        }.execute();
    }


    public static void callHttpLike(String postId, boolean likeType, TextView tvLike, TextView tvDislike){
        String authorization = "Token " + Networking.getToken();

        if(likeType){
            final Call<Like> response = ListRestAdapter.getInstance().postLikeData(authorization, postId);
            response.enqueue(new Callback<Like>() {
                @Override
                public void onResponse(Call<Like> call, Response<Like> response) {
                    String resLike = response.body().getLike_count();
                    String resDislike = response.body().getDislike_count();
                    likeChanged(tvLike,tvDislike, resLike, resDislike);
                }

                @Override
                public void onFailure(Call<Like> call, Throwable t) {

                }
            });

        }else{
            final Call<Dislike> response = ListRestAdapter.getInstance().postDislikeData(authorization, postId);
            response.enqueue(new Callback<Dislike>() {
                @Override
                public void onResponse(Call<Dislike> call, Response<Dislike> response) {
                    String res = response.body().getDislike_count();
                    String resLike = response.body().getLike_count();
                    String resDislike = response.body().getDislike_count();
                    likeChanged(tvLike,tvDislike, resLike, resDislike);
                }

                @Override
                public void onFailure(Call<Dislike> call, Throwable t) {

                }
            });
        }

    }




}
