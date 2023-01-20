package com.github.ccloud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    }
}
