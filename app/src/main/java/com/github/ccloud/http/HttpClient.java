package com.github.ccloud.http;

import com.github.ccloud.util.AuthUtil;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpClient {

    public static OkHttpClient create() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(chain -> {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("Authorization", AuthUtil.getToken());
            Response response = chain.proceed(chain.request()).newBuilder().headers(buildHeaders(chain.request(), hashMap)).build();
            return response;
        });

        SSLContext sslContext = null;
        X509TrustManager allTrustManager = getTrustAllCerts();
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{ allTrustManager }, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        } catch (KeyManagementException e) {
            throw new AssertionError(e);
        }

        builder.sslSocketFactory(sslContext.getSocketFactory(), allTrustManager);

        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.writeTimeout(30, TimeUnit.SECONDS);
        builder.connectTimeout(30, TimeUnit.SECONDS);

        return builder.build();
    }

    private static Headers buildHeaders(Request request, Map<String, String> headerMap) {
        Headers headers = request.headers();
        if (headers != null) {
            Headers.Builder builder = headers.newBuilder();
            for (String key : headerMap.keySet()) {
                builder.add(key, headerMap.get(key));
            }
            return builder.build();
        }

        return null;
    }

    private static X509TrustManager getTrustAllCerts() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[]{};
            }
        };
    }
}
