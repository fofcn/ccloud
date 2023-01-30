package com.github.ccloud;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends Activity {

    private RadioGroup navigation;

    private ViewPager content;

    private List<View> navViews;

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

    private void initView() {
        navViews = new ArrayList<>();
        navViews.add(LayoutInflater.from(this).inflate(R.layout.idx_photo,null));
        navViews.add(LayoutInflater.from(this).inflate(R.layout.idx_video,null));
        navViews.add(LayoutInflater.from(this).inflate(R.layout.idx_other,null));
        navViews.add(LayoutInflater.from(this).inflate(R.layout.idx_settings,null));

        RadioButton rbPhoto = findViewById(R.id.rb_photos);
        RadioButton rbVideo = findViewById(R.id.rb_videos);
        RadioButton rbOther = findViewById(R.id.rb_other);
        RadioButton rbSetting = findViewById(R.id.rb_settings);

        content.setAdapter(new CustomPagerAdapter());
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

    private class CustomPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return navViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(navViews.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(navViews.get(position));
            return navViews.get(position);
        }
    }
}


