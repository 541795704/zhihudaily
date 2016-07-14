package com.project.zhihudaily.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.zhihudaily.R;

public class WelcomeActivity extends AppCompatActivity{
    private ImageView image_Welcome;
    private TextView title_welcome;
    private boolean flag = true;//设置一个标记用来是否第一次打开程序
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        //旋转动画
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setFillAfter(true);
        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1500);
        scaleAnimation.setFillAfter(true);
        //渐变动画
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1500);
        alphaAnimation.setFillAfter(true);
        //动画集
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        //开启动画
        relativeLayout.startAnimation(animationSet);
        scaleAnimation.setFillAfter(true);
        new Thread(){
            @Override
            public void run(){
                super.run();
                //睡眠3秒后跳转
                SystemClock.sleep(3 * 1000);
                //读取本地文件Welcome
                SharedPreferences sharedPreferences = getSharedPreferences("Welcome", MODE_PRIVATE);
                //获得isFirstLogin的值，true跳转到引导页面，false跳转到主界面
                boolean isFirstLogin = sharedPreferences.getBoolean("isFirstLogin", true);
                if(isFirstLogin){
                    startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
                    WelcomeActivity.this.finish();
                }else{
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    WelcomeActivity.this.finish();
                }
            }
        }.start();
    }

    //初始化控件
    private void initView(){
        image_Welcome = (ImageView) findViewById(R.id.image_Welcome);
        title_welcome = (TextView) findViewById(R.id.title_welcome);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout_welcome);
    }

    @Override
    protected void onPause(){
        super.onPause();
        flag = false;
    }
}
