package com.hm.project_glue.util.http;

import com.hm.project_glue.main.info.Data.InfoData;
import com.hm.project_glue.main.list.data.PostData;
import com.hm.project_glue.util.write.data.GroupListData;
import com.hm.project_glue.util.write.data.GroupListResults;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by HM on 2016-12-03.
 */

public interface IServerData {
/******************************** Member API ********************************/
    //회원가입
    @Multipart
    @POST("/member/signup/")
    Call<PostData> signUpData( @Field("phone_number") String phone_number,
                               @Field("email") String email,
                               @Field("name") String name,
                               @Field("password") String password,
                               @PartMap Map<String, RequestBody> files);
    // 로그인
    @POST("/member/login/")
    Call<String> loginData( @Field("phone_number") String phone_number,
                            @Field("password") String password);

    //로그아웃
    @GET("/member/logout/")
    Call<String> logOutData(@Header("Authorization") String authorization);

    // 내정보 보기
    @GET("/member/myinfo/")
    Call<InfoData> myInfoData(@Header("Authorization") String authorization);
    // 내정보 수정
    @PUT("/member/myinfo/{userId}/")
    Call<String> myInfoUpdateData(@Header("Authorization") String authorization,
                                  @Path("userId") String userId,
                                  @Field("phone_number") String phone_number,
                                  @Field("email") String email,
                                  @Field("name") String name,
                                  @Field("password") String password);
    // 내정보 삭제
    @DELETE("/member/myinfo/{userId}/")
    Call<String> myInfoDeleteData(@Header("Authorization") String authorization,
                                  @Path("userId") String userId);

    //아이디 중복 확인 (결과값 boolean)
    @GET("/member/id_check/")
    Call<String> checkId(@Query("phone_number")String phone_number);

/******************************** Group API ********************************/
    // 구룹 리스트
    @GET("/group/group_list/")
    Call<GroupListData> getGroupListData(@Header("Authorization") String authorization);

    // 구룹 생성
    @Multipart
    @POST("/group/group_list/")
    Call<ArrayList<GroupListResults>> createGroupData(@Header("Authorization") String authorization,
                                                      @Field("name") String name, //20자
                                                      @PartMap Map<String, RequestBody> group_image);
    //구룹 탈퇴
    @POST("/group/group_leave/{group_id}/")
    Call<PostData> groupLeavetData(@Header("Authorization") String authorization,
                                   @Path("groupId") String groupId,
                                   @Field("candidate_id") String candidate_id);

    // 구룹 삭제 ( 방장 전용 )
    @POST("/group/group_delete/{group_id}/")
    Call<PostData> groupDeleteData(@Header("Authorization") String authorization,
                                   @Path("groupId") String groupId);
    // 구룹 수정 ( 방장 전용 )
    @Multipart
    @POST("/group/group_update/{group_id}/")
    Call<PostData> groupUpdateData(@Header("Authorization") String authorization,
                                   @Path("groupId") String groupId,
                                   @Field("group_name") String group_name,
                                   @PartMap Map<String, RequestBody> group_image);

    @Multipart
    @POST("/group/group_invite/{group_id}/")
    Call<PostData> groupInviteData(@Header("Authorization") String authorization,
                                   @Path("groupId") String groupId,
                                   @PartMap Map<String, String> phone_number);

/******************************** Post API ********************************/
    //Post 올리기
    @Multipart
    @POST("/posts/post_list/{groupId}/")
    Call<PostData> postingData(     @Header("Authorization") String authorization,
                                    @Path("groupId") String groupId,
                               //content, group
                                    @PartMap Map<String, RequestBody> params,
                                    @PartMap Map<String, RequestBody> photos);
    // Post 목록 보기  QueryMap-> ex) page=2
    @GET("/posts/post_list/{groupId}/")
    Call<PostData> getListData(     @Header("Authorization") String authorization,
                                    @Path("groupId") String groupId,
                                    @QueryMap Map<String, String> page);
    // 좋아요
    @POST("/posts/post_like/{post_id}/")
    Call<PostData> postLikeData(    @Header("Authorization") String authorization,
                                    @Path("post_id") String post_id);
    // 싫어요
    @POST("/posts/post_dislike/{post_id}/")
    Call<PostData> postDislikeData( @Header("Authorization") String authorization,
                                    @Path("post_id") String post_id);
    // post 삭제
    @DELETE("/posts/post_detail/{post_id}/")
    Call<PostData> postDeleteData(  @Header("Authorization") String authorization,
                                    @Path("post_id") String post_id);
    // post 수정
    @PATCH("/posts/post_detail/{post_id}/")
    Call<PostData> postDeleteData(     @Header("Authorization") String authorization,
                                       @Path("post_id") String post_id,
                                       //content, group
                                       @PartMap Map<String, RequestBody> params,
                                       @PartMap Map<String, RequestBody> photos,
                                       //Photo삭제 ( photo의 pk 전달 )
                                       @PartMap Map<String, String> delete_photos);
    // post Detail
    @GET("/posts/post_detail/{post_id}/")
    Call<PostData> postDetailData(     @Header("Authorization") String authorization,
                                       @Path("post_id") String post_id);


}
