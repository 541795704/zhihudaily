package com.project.zhihudaily.Adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by tian on 2016/7/4.
 */
// GuideViewpager设置adapter
public class GuideViewpagerAdapter extends PagerAdapter{
    private List<ImageView> list;

    public GuideViewpagerAdapter(List<ImageView> list){
        this.list = list;
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        ImageView imageView = list.get(position);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((View) object);
    }
}
