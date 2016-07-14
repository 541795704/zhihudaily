package com.project.zhihudaily.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.project.zhihudaily.Adapter.GuideViewpagerAdapter;
import com.project.zhihudaily.R;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity{

    private int imgs[] = {R.drawable.welcome1, R.drawable.welcome2, R.drawable.welcome3}; //添加引导图片
    private LinearLayout ll_point_guide;
    private ViewPager viewpager_guide;
    private int currentPosition;//记录当前页数
    private int prePosition;//记录前一位的页数
    private int pagestate;//记录换页的状态

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //存取一个标签到本地文件
        getSharedPreferences("Welcome", MODE_PRIVATE).edit().putBoolean("isFirstLogin", false).commit();
        initView();
    }

    private void initView(){
        viewpager_guide = (ViewPager) findViewById((R.id.viewpager_guide));
        ll_point_guide = (LinearLayout) findViewById(R.id.ll_point_guide);
        List<ImageView> imageViewList = new ArrayList<>();
        for(int i = 0; i < imgs.length; i++){
            //创建imageview加载图片
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageResource(imgs[i]);
            imageViewList.add(imageView);
            //创建小点
            View point_view = new View(this);
            point_view.setBackgroundResource(R.drawable.selecter_point);
            //设置默认没选中
            point_view.setEnabled(false);
            //设置控件的宽高(把px转换位dp)
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            if(i != 0){
                params.leftMargin = 20;
            }
            point_view.setLayoutParams(params);
            //吧小点添加到LinearLayout
            ll_point_guide.addView(point_view);
        }
        //设置第一个点为选中
        ll_point_guide.getChildAt(0).setEnabled(true);
        GuideViewpagerAdapter guideViewpagerAdapter = new GuideViewpagerAdapter(imageViewList);
        viewpager_guide.setAdapter(guideViewpagerAdapter);
        //给viewpager设置翻页监听
        viewpager_guide.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
                currentPosition = position;
            }

            @Override
            public void onPageSelected(int position){
                //把前一个位置设置为false
                ll_point_guide.getChildAt(prePosition).setEnabled(false);
                //把当前位置设置为true
                ll_point_guide.getChildAt(position).setEnabled(true);
                //设置前一个位置等于当前位置
                prePosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state){
                //state从1-->0只会是第一页或者最后一页
                if(state == 0 && pagestate == 1 && currentPosition == imgs.length - 1){
                    //跳转到mainactivity
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    GuideActivity.this.finish();
                }
                pagestate = state;//把state状态记录
            }
        });
    }
}
