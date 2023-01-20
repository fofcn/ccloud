package com.github.ccloud.http.client;

import com.github.ccloud.http.HttpConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    public static <T> T init(Class<T> httpClass) {
        //构建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求BaseUrl地址
                .baseUrl(HttpConfig.API_HOST)
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(httpClass);
    }
}
