package com.project.zhihudaily.App;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2016/7/7.
 */
public class MyApp extends Application{
    @Override
    public void onCreate(){
        super.onCreate();
        Fresco.initialize(this);//初始化Fresco
        Bmob.initialize(this, "fa1857b59e041c2f9b8b17e65118e1bc");
    }
}
