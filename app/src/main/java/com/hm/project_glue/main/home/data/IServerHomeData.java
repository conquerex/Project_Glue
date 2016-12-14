package com.hm.project_glue.main.home.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by jongkook on 2016. 12. 6..
 */

public interface IServerHomeData {

    // GET 요청
    @GET("/group/group_list/")
    // Call : retrofit에서 정의한 HTTP request와 response의 쌍을 지닌 객체
    // HTTP 통신을 통해 수신한 JSON 등이 변환될 객체
    Call<HomeData> getData(
            // 헤더 : 요청, 응답 그리고 리소스에 대한 메타 정보를 전달
            @Header("Authorization") String token);
}
