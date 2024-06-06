package com.github.ccloud.ui;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.ccloud.R;
import com.github.ccloud.constant.HostConstant;
import com.github.ccloud.entity.Response;
import com.github.ccloud.http.api.AccountHttpApi;
import com.github.ccloud.http.client.HttpClient;
import com.github.ccloud.util.AuthUtil;
import com.github.ccloud.util.ContextHolder;
import com.github.ccloud.util.SpUtil;

import retrofit2.Call;
import retrofit2.Callback;

public class HostConfigActivity extends AppCompatActivity {

    private Button hostConfigBtn;

    private EditText hostEditText;
    private ProgressBar progressBar;

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkConfigAndToken();
        int hasReadStoragePermission = ContextCompat.checkSelfPermission(getApplication(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadStoragePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(TAG, "Permission request result " + requestCode);
        if (requestCode == 0) {

        }
    }



    private void checkConfigAndToken() {
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
                SpUtil.getInstance().save(HostConstant.HOST_ADDRESS_KEY, hostEditText.getText().toString());

                startActivity(new Intent(ContextHolder.getContext(), LoginActivity.class));
            });
        } else {
            ProgressDialog progressDialog = new ProgressDialog(HostConfigActivity.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            // 检查是否Token
            String token = AuthUtil.getToken();
            if ("".equals(host)) {
                startActivity(new Intent(ContextHolder.getContext(), LoginActivity.class));
            } else {
                // 验证Token是否过期
                AccountHttpApi accountApi = HttpClient.init(AccountHttpApi.class);
                Call<Response<Void>> resp = accountApi.validToken(token);
                resp.enqueue(new Callback<Response<Void>>() {
                    @Override
                    public void onResponse(Call<Response<Void>> call, retrofit2.Response<Response<Void>> response) {
                        progressDialog.dismiss();
                        Response resp = response.body();
                        if (resp.isSuccess()) {
                            // token 有效则跳转到首页
                            startActivity(new Intent(ContextHolder.getContext(), IndexActivity.class));
                        } else {
                            // token无效则跳转到登录页
                            AuthUtil.deleteToken();
                            startActivity(new Intent(ContextHolder.getContext(), LoginActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<Void>> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("Network", "网络错误：" + t.getMessage() + "," + t);
                        Toast.makeText(HostConfigActivity.this, "网络不给力，请重试", Toast.LENGTH_SHORT).show();
                        Intent loginIntent = new Intent(ContextHolder.getContext(), LoginActivity.class);
                        loginIntent.putExtra("name", "Ford Ji");
                        startActivity(loginIntent);
                    }
                });

            }
        }
    }
}
