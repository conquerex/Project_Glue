package com.hm.project_glue.main.list.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by HM on 2016-12-03.
 */

public interface IServerListData {
    @Headers("Authorization: Token 66f35eb79581418f4d3eff6a0949c061c110d576")
    @GET("/posts/post_list/3/")
    Call<PostData> getData();
}
