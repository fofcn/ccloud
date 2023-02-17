package com.github.ccloud.view.index;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>(4);

    public CustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments.add(new PhotoFragment());
        this.fragments.add(new VideoFragment());
        this.fragments.add(new OtherFragment());
        this.fragments.add(new SettingFragment());
    }

    @NonNull
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    public int getItemCount() {
        return fragments.size();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
