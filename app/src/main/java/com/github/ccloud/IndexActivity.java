package com.github.ccloud;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class IndexActivity extends Activity {

    private RadioGroup navigation;

    private ViewPager content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        content = findViewById(R.id.viewpager);
        navigation = findViewById(R.id.rb_navigation);

        // 设置RadioGroup监听
        navigation.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_photos:
                    content.setCurrentItem(0);
                    break;
                case R.id.rb_videos:
                    content.setCurrentItem(1);
                    break;
                case R.id.rb_other:
                    content.setCurrentItem(2);
                    break;
                case R.id.rb_settings:
                    content.setCurrentItem(3);
                    break;
            }

        });
    }
}
