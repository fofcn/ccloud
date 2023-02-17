package com.github.ccloud.http.api;

import com.github.ccloud.entity.Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IndexHttpApi {

    @GET("/")
    Call<Response<String>> index();
}
