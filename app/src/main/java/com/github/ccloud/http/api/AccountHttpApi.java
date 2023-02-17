package com.github.ccloud.http.api;

import com.github.ccloud.constant.HostConstant;
import com.github.ccloud.entity.Response;
import com.github.ccloud.entity.cmd.LoginCmd;
import com.github.ccloud.entity.dto.LoginDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AccountHttpApi {

    @POST("/account/login")
    Call<Response<LoginDto>> accountLogin(@Body LoginCmd cmd);

    @GET("/account/token/valid")
    Call<Response<Void>> validToken(@Header(HostConstant.TOKEN_HEADER_KEY) String token);
}
