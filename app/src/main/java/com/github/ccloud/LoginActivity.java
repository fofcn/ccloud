package com.github.ccloud;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends Activity {

    private Button loginBtn;

    private Button testBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.btnLogin);
        loginBtn.setOnClickListener(view -> {
            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_index);
        });

        testBtn = findViewById(R.id.btnLogin);

        testBtn.setOnClickListener(view -> {
            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_index);
        });
    }
}
