package com.github.ccloud.http.factory;

import com.github.ccloud.util.ConfigUtil;

import java.util.concurrent.ConcurrentHashMap;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpApiFactory {

    public static volatile HttpApiFactory instance;

    private OkHttpClient okHttpClient;

    private Converter.Factory[] converterFactory;

    private final ConcurrentHashMap<String, Object> httpApiCacheTable = new ConcurrentHashMap<>();

    public static HttpApiFactory getInstance() {
        synchronized (HttpApiFactory.class) {
            if (instance == null) {
                instance = new HttpApiFactory();
            }
        }

        return instance;
    }

    public <API> API createHttpApi(Class<API> apiClass) {
        API httpApi = (API) httpApiCacheTable.get(apiClass.getCanonicalName());
        if (httpApi == null) {
            //构建Retrofit实例
            Retrofit retrofit = new Retrofit.Builder()
                    //设置网络请求BaseUrl地址
                    .baseUrl(ConfigUtil.getHost())
                    //设置数据解析器
                    .addConverterFactory(converterFactory == null ? GsonConverterFactory.create() : converterFactory[0])
                    //设置Http客户端
                    .client(okHttpClient)
                    .build();

            httpApi = retrofit.create(apiClass);

            httpApiCacheTable.putIfAbsent(apiClass.getCanonicalName(), httpApi);
        }

        return httpApi;
    }

    public HttpApiFactory setOkHttpClient(final OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
        return this;
    }

    public HttpApiFactory setConverterFactory(Converter.Factory... factories) {
        this.converterFactory = factories;
        return this;
    }
}
