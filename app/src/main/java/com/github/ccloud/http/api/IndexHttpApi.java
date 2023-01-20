package com.github.ccloud.http.api;

import android.database.Observable;

import com.github.ccloud.http.entity.Response;
import com.github.ccloud.http.entity.dto.LoginDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IndexHttpApi {

    @GET("/")
    Call<Response<String>> index();
}
