package com.github.ccloud.http.api;

import com.github.ccloud.entity.Response;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface FileHttpApi {

    @POST("/file/upload")
    Call<Response<Void>> upload(@Body RequestBody body,
                                @Header("Authorization") String token);
}
