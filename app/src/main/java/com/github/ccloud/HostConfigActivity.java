package com.github.ccloud;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ccloud.constant.HostConstant;
import com.github.ccloud.http.api.AccountHttpApi;
import com.github.ccloud.http.client.HttpClient;
import com.github.ccloud.http.entity.Response;
import com.github.ccloud.util.ContextHolder;
import com.github.ccloud.util.SpUtil;

import retrofit2.Call;
import retrofit2.Callback;

public class HostConfigActivity extends AppCompatActivity {

    private Button hostConfigBtn;

    private EditText hostEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //检查是否配置CCloud URL
        String host = SpUtil.getInstance().getString(HostConstant.HOST_ADDRESS_KEY, "");
        if ("".equals(host)) {
            setContentView(R.layout.activity_hostconfig);
            hostConfigBtn = findViewById(R.id.btnHostConfig);
            hostEditText = findViewById(R.id.editHostConfig);
            hostConfigBtn.setOnClickListener(view -> {
                Log.i("HostConfig", "Host address: " + hostEditText.getText().toString());
                if (hostEditText.getText().toString().equals("")) {
                    Toast.makeText(ContextHolder.getContext(), "请输入CCloud主机地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 保存Host address
                SpUtil.getInstance().save(HostConstant.HOST_ADDRESS_KEY, host);

                Intent intent = new Intent();
                intent.setClass(ContextHolder.getContext(), LoginActivity.class);
                startActivity(intent);
            });
        } else {
            // 检查是否Token
            String token = SpUtil.getInstance().getString(HostConstant.ACCOUNT_TOKEN_KEY, "");
            if ("".equals(host)) {
                setContentView(R.layout.activity_login);
            } else {
                // 验证Token是否过期
                AccountHttpApi accountApi = HttpClient.init(AccountHttpApi.class);
                Call<Response<Void>> resp = accountApi.validToken(token);
                resp.enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(Call<Response<Void>> call, retrofit2.Response<Response<Void>> response) {
                        Response resp = response.body();
                        if (resp.isSuccess()) {
                            setContentView(R.layout.activity_index);
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<Void>> call, Throwable t) {
                        Log.e("Network", "网络错误：" + t.getMessage() + "," + t);
                        Toast.makeText(HostConfigActivity.this, "post回调失败", Toast.LENGTH_SHORT).show();
                        setContentView(R.layout.activity_login);
                    }
                });

            }
        }
    }
}
