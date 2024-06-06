package com.github.ccloud.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.github.ccloud.R;
import com.github.ccloud.ui.index.CustomFragmentPagerAdapter;
import com.github.ccloud.ui.photo.Synchronizer;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity  {

    private RadioGroup navigation;

    private ViewPager content;

    private List<View> navViews;

    private CustomFragmentPagerAdapter fragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Synchronizer.getInstance().startSync();

        fragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());
        content = findViewById(R.id.viewpager);
        content.setAdapter(fragmentPagerAdapter);

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

        initView();
    }

    private void initView() {
        navViews = new ArrayList<>();
        navViews.add(LayoutInflater.from(this).inflate(R.layout.idx_photo, content));
        navViews.add(LayoutInflater.from(this).inflate(R.layout.idx_video, content));
        navViews.add(LayoutInflater.from(this).inflate(R.layout.idx_other, content));
        navViews.add(LayoutInflater.from(this).inflate(R.layout.idx_setting, content));

        RadioButton rbPhoto = findViewById(R.id.rb_photos);
        RadioButton rbVideo = findViewById(R.id.rb_videos);
        RadioButton rbOther = findViewById(R.id.rb_other);
        RadioButton rbSetting = findViewById(R.id.rb_settings);

        content.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rbPhoto.setChecked(true);
                        rbVideo.setChecked(false);
                        rbOther.setChecked(false);
                        rbSetting.setChecked(false);
                        break;
                    case 1:
                        rbPhoto.setChecked(false);
                        rbVideo.setChecked(true);
                        rbOther.setChecked(false);
                        rbSetting.setChecked(false);
                        break;
                    case 2:
                        rbPhoto.setChecked(false);
                        rbVideo.setChecked(false);
                        rbOther.setChecked(true);
                        rbSetting.setChecked(false);
                        break;
                    case 3:
                        rbPhoto.setChecked(false);
                        rbVideo.setChecked(false);
                        rbOther.setChecked(false);
                        rbSetting.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
