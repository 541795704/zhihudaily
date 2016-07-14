package com.project.zhihudaily.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.project.zhihudaily.R;
import com.project.zhihudaily.Utils.Api;

public class CollectDetailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_detail);
        Intent intent = getIntent();
        //获取传值过来的ID
        int id = intent.getIntExtra("id", 0);
        WebView webView = (WebView) findViewById(R.id.webview_collectdetail);
        //设置WebView的一些缩放功能点
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setHorizontalScrollBarEnabled(false);
        //设置WebView可触摸放大缩小
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小`
        webView.getSettings().setUseWideViewPort(true);
        //允许JS执行
        webView.getSettings().setJavaScriptEnabled(true);
        //加载URL
        webView.loadUrl(Api.ZHIHU_DAILY_BASE_URL + id );
    }
}
