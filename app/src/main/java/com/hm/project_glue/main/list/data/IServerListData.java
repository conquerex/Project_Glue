package com.hm.project_glue.main.list.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by HM on 2016-12-03.
 */

public interface IServerListData {

    @GET("/posts/post_list/{groupId}/")
    Call<PostData> getListData(
            @Header("Authorization") String authorization,
            @Path("groupId") String groupId);
}
