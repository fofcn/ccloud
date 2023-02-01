package com.github.ccloud.http.client;

import com.github.ccloud.constant.HostConstant;
import com.github.ccloud.util.SpUtil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {

    public static <T> T init(Class<T> httpClass) {
        //构建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                //设置网络请求BaseUrl地址
                .baseUrl(SpUtil.getInstance().getString(HostConstant.HOST_ADDRESS_KEY, ""))
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
//todo customize okhttp client                .client()
                .build();

        return retrofit.create(httpClass);
    }
}
