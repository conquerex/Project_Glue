package com.hm.project_glue.util.http;

import com.hm.project_glue.main.list.data.PostData;
import com.hm.project_glue.util.write.data.GroupListResults;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
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

    @GET("/group/group_list/")
    Call<ArrayList<GroupListResults>> getGroupListData(
                    @Header("Authorization") String authorization
            );


    @Multipart
    @POST("/posts/post_list/{groupId}/")
    Call<PostData> postingData(
            @Header("Authorization") String authorization,
            @Path("groupId") String groupId,
            @PartMap Map<String, RequestBody> params,
            @PartMap Map<String, RequestBody> files
    );

}
