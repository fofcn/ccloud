package com.github.ccloud.http.api;

import android.database.Observable;

import com.github.ccloud.http.entity.Response;
import com.github.ccloud.http.entity.dto.LoginDto;

import retrofit2.http.POST;

public interface AccountHttpApi {

    @POST("/account/login")
    Observable<Response<LoginDto>> accountLogin();
}
