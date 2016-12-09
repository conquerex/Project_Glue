package com.hm.project_glue.main.home;

import android.util.Log;

import com.hm.project_glue.R;
import com.hm.project_glue.main.home.data.IServerHomeData;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jongkook on 2016. 12. 7..
 */

public class HomeRestAdapter {
    public static final int CONNECT_TIMEOUT = 3;
    public static final int WRITE_TIMEOUT = 5;
    public static final int READ_TIMEOUT = 3;
    private static String TAG = "HomeRestAdapter";
    private static OkHttpClient client;
    private static IServerHomeData service;

    public synchronized static IServerHomeData getInstance(){
        String SERVER_URL = R.string.BASE_URL + "";
        Log.i(TAG, "----------- SERVER_URL ---- "+ SERVER_URL);

        if(service.equals(null)){
            // 통신 로그를 확인하기 위한 interceptor 설정
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            CookieManager manager = new CookieManager();
            manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

            // OK HTTP 설정
            // SSL 통한 서버연결인 경우 인증서가 없으면 통신자체가 안된다.
            // 이럴 경우 우회하기 위한 인증서를 무시하는 셋팅.
            client = configureClient(new OkHttpClient().newBuilder())
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .cookieJar(new JavaNetCookieJar(manager)) //쿠키 저장
                    .addInterceptor(interceptor) // 로그를 출력(디버깅용)
                    .build();

            // Retrofit 설정
            service = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create()) // json 응답 변환. Json Parser 추가
                    .build().create(IServerHomeData.class); //인터페이스 연결
        }
        return service;
    }

    // Https를 이용시 서버 인증의 인증 문제로 SSL 오류 발
    // client에 서버 인증서가 없기 때문 --> 인증서 우회. UnCertificated 허용
    public static OkHttpClient.Builder configureClient(final OkHttpClient.Builder builder) {

        X509TrustManager x509TrustManager = new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
            }

            @Override
            public void checkClientTrusted(final X509Certificate[] chain,
                                           final String authType) {
            }
        };

        final TrustManager[] certs = new TrustManager[]{x509TrustManager};

        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(null, certs, new SecureRandom());
        } catch (final java.security.GeneralSecurityException ex) {
            ex.printStackTrace();
        }

        try {
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) {
                    return true;
                }
            };

            builder.sslSocketFactory(ctx.getSocketFactory(), x509TrustManager).hostnameVerifier(hostnameVerifier);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return builder;
    }
}
