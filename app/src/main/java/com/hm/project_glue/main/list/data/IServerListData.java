package com.hm.project_glue.main.list.data;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by HM on 2016-12-03.
 */

public interface IServerListData {

    @GET("/posts/post_list/{groupId}/")
    Call<PostData> getListData(
            @Header("Authorization") String authorization,
            @Path("groupId") String groupId,
            @QueryMap Map<String, String> params
    );
}
