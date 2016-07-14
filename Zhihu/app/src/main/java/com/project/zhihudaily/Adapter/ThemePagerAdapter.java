package com.project.zhihudaily.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.project.zhihudaily.Bean.Theme;

import java.util.List;

/**
 * Created by tian on 2016/7/5.
 */
public class ThemePagerAdapter extends FragmentPagerAdapter{
    private List<Theme> titles;
    private List<Fragment> fragments;

    public ThemePagerAdapter(FragmentManager fm, List<Theme> titles, List<Fragment> fragments){
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position){
        return fragments.get(position);
    }

    @Override
    public int getCount(){
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position){
        return titles.get(position).getName();
    }
}
