package com.hm.project_glue.util.http;

import com.hm.project_glue.util.Networking;

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
 * Created by HM on 2016-12-03.
 */

public class ListRestAdapter {

    public static final int CONNECT_TIMEOUT = 3;
    public static final int WRITE_TIMEOUT = 10;
    public static final int READ_TIMEOUT = 5;
    private static String TAG = "TEST";
    private static OkHttpClient client;
    private static IServerListData service;

    public synchronized static IServerListData getInstance(){
        if(service == null) {
            // 통신 로그를 확인하기 위한 interceptor 설정
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // 쿠키 매니저 설정
            CookieManager manager = new CookieManager();
            manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

            //OK HTTP 설정
            //SSL 통한 서버연결인 경우 인증서가 없으면 통신자체가 안된다.
            // 이럴 경우 우회하기 위한 인증서를 무시하는 셋팅.
            client = configureClient(new OkHttpClient().newBuilder())
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)

                    .cookieJar(new JavaNetCookieJar(manager)) //쿠키 저장
                    .addInterceptor(interceptor) // 로그를 출력(디버깅용)
                    .build();

            service = new Retrofit.Builder()
                    .baseUrl(Networking.getBASE_URL())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(IServerListData.class);
        }
        return service;
    }

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