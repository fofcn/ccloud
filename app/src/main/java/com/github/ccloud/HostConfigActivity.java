package com.github.ccloud;

import android.content.Intent;
import android.database.Observable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ccloud.http.api.IndexHttpApi;
import com.github.ccloud.http.client.HttpClient;
import com.github.ccloud.http.entity.Response;

import retrofit2.Call;
import retrofit2.Callback;

public class HostConfigActivity extends AppCompatActivity {

    private Button hostConfigBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostconfig);
        hostConfigBtn = findViewById(R.id.btnHostConfig);
        hostConfigBtn.setOnClickListener(view -> {
            Toast.makeText(HostConfigActivity.this, "服务地址配置按钮被点击", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(HostConfigActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        IndexHttpApi indexApi = HttpClient.init(IndexHttpApi.class);
        Call<Response<String>> resp = indexApi.index();
        resp.enqueue(new Callback<Response<String>>() {
            @Override
            public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                Toast.makeText(HostConfigActivity.this, "post回调成功:异步执行", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Response<String>> call, Throwable t) {
                Log.e("HTTPClient", "post回调失败：" + t.getMessage() + "," + t.toString());
                Toast.makeText(HostConfigActivity.this, "post回调失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
