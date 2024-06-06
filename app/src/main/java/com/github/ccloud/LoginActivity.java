package com.github.ccloud;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.ccloud.entity.dto.LoginDto;
import com.github.ccloud.util.AuthUtil;
import com.github.ccloud.view.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText editUsername;
    private EditText editPassword;
    private LoginViewModel loginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.btnLogin);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        ProgressDialog loginProcessDialog = new ProgressDialog(this);
        loginProcessDialog.setMessage("正在登录...");
        loginProcessDialog.setCancelable(false);

        Intent intent = getIntent();
        if (intent != null) {
            Toast.makeText(this, intent.getStringExtra("name"), Toast.LENGTH_SHORT).show();
        }

        loginBtn.setOnClickListener(view -> {
            // 获取用户名和密码
            String username = editUsername.getText().toString();
            String password = editPassword.getText().toString();
            loginProcessDialog.show();
            loginViewModel.login(username, password);

            loginViewModel.getLoginResponse().observe(this, loginDtoResponse -> {
                loginProcessDialog.dismiss();

                if (loginDtoResponse != null && loginDtoResponse.isSuccess()) {
                    LoginDto dto = loginDtoResponse.getData();
                    AuthUtil.saveToken(dto.getToken());
                    AuthUtil.saveUserId(dto.getId());
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, IndexActivity.class));
                } else {
                    Log.e("Account", "login failed, error code: " + loginDtoResponse.getErrorCode() + " error message: " + loginDtoResponse.getErrorMsg());
                    Toast.makeText(LoginActivity.this, "登录失败：" + loginDtoResponse.getErrorMsg(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, IndexActivity.class));
                }
            });

            loginViewModel.getLoginError().observe(this, new Observer<Throwable>() {
                @Override
                public void onChanged(Throwable throwable) {
                    loginProcessDialog.dismiss();  // 隐藏ProgressDialog
                    if (throwable != null) {
                        Log.e("HTTPClient", "post回调失败：" + throwable.getMessage() + ", " + throwable);
                        Toast.makeText(LoginActivity.this, "网络不给力，请重试", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, IndexActivity.class));
                    }
                }
            });
//            // 调用接口
//            AccountHttpApi accountApi = HttpClient.init(AccountHttpApi.class);
//            Call<Response<LoginDto>> resp = accountApi.accountLogin(new LoginCmd(username, password));
//            resp.enqueue(new Callback<Response<LoginDto>>() {
//                // 验证返回接口
//                @Override
//                public void onResponse(Call<Response<LoginDto>> call, retrofit2.Response<Response<LoginDto>> response) {
//                    Response<LoginDto> loginDto = response.body();
//                    // 如果成功则-》存储token-》跳转页面
//                    if (loginDto.isSuccess()) {
//                        LoginDto dto = loginDto.getData();
//                        AuthUtil.saveToken(dto.getToken());
//                        AuthUtil.saveUserId(dto.getId());
//                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(ContextHolder.getContext(), IndexActivity.class));
//
//                    } else {
//                        // 如果失败则-》toast提示
//                        Log.e("Account", "login failed, error code: " + loginDto.getErrorCode() + " error message: " + loginDto.getErrorMsg() + "");
//                        Toast.makeText(ContextHolder.getContext(), "登录失败：" + loginDto.getErrorMsg(), Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(ContextHolder.getContext(), IndexActivity.class));
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Response<LoginDto>> call, Throwable t) {
//                    Log.e("HTTPClient", "post回调失败：" + t.getMessage() + "," + t);
//                    Toast.makeText(ContextHolder.getContext(), "网络不给力，请重试", Toast.LENGTH_SHORT).show();
//                }
//            });

        });
    }
}
