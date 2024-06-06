package com.github.ccloud.view.viewmodel;

import android.accounts.Account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.ccloud.entity.Response;
import com.github.ccloud.entity.cmd.LoginCmd;
import com.github.ccloud.entity.dto.LoginDto;
import com.github.ccloud.http.api.AccountHttpApi;
import com.github.ccloud.http.client.HttpClient;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<Response<LoginDto>> loginResponse = new MutableLiveData<>();
    private final MutableLiveData<Throwable> loginError = new MutableLiveData<>();

    public LiveData<Response<LoginDto>> getLoginResponse() {
        return loginResponse;
    }

    public LiveData<Throwable> getLoginError() {
        return loginError;
    }

    public void login(String username, String password) {
        AccountHttpApi accountHttpApi = HttpClient.init(AccountHttpApi.class);
        Call<Response<LoginDto>> resp = accountHttpApi.accountLogin(new LoginCmd(username, password));
        resp.enqueue(new Callback<Response<LoginDto>>() {
            @Override
            public void onResponse(Call<Response<LoginDto>> call, retrofit2.Response<Response<LoginDto>> response) {
                loginResponse.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Response<LoginDto>> call, Throwable t) {
                loginError.postValue(t);
            }
        });
    }

}
