package com.project.zhihudaily.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.project.zhihudaily.Bean.GuokerDetailBean;
import com.project.zhihudaily.R;
import com.project.zhihudaily.Utils.Api;
import com.project.zhihudaily.Utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GuokrDetailActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private TextView guok_name;
    private TextView guok_title;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton floatingActionButton;
    private ImageView ivheadline;
    private TextView content;
    private String id;
    private String title;
    private String headline_img_tb;
    private GuokerDetailBean bean;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 0:
                    bean = (GuokerDetailBean) msg.obj;
                    content.setText(Html.fromHtml(bean.getContent()));
                    guok_name.setText(bean.getName());
                    guok_title.setText(bean.getTitle());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guokr_detail);
        ShareSDK.initSDK(this);//初始化分享
        initView();
        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        title = intent.getStringExtra("title");
        headline_img_tb = intent.getStringExtra("headline_img_tb");
        toolbar.setTitle("");
        if(headline_img_tb != null){
            //加载图片
            Glide.with(this).load(headline_img_tb).asBitmap().centerCrop().into(ivheadline);
        }else{
            ivheadline.setImageResource(R.drawable.welcome);
        }
        //给FloatingActionButton 设置监听 分享
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //点击分享
                OnekeyShare oks = new OnekeyShare();
                //关闭sso授权
                oks.disableSSOWhenAuthorize();
                // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
                oks.setTitleUrl(Api.GUOKR_ARTICLE_LINK_V1 + id);
                // text是分享文本，所有平台都需要这个字段
                oks.setText(title + "/n" + Api.GUOKR_ARTICLE_LINK_V1 + id);
                // url仅在微信（包括好友和朋友圈）中使用
                oks.setUrl(Api.GUOKR_ARTICLE_LINK_V1 + id);
                // site是分享此内容的网站名称，仅在QQ空间使用
                oks.setSite(getString(R.string.app_name));
                // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                oks.setSiteUrl(Api.GUOKR_ARTICLE_LINK_V1 + id);
                // 启动分享GUI
                oks.show(getApplicationContext());
            }
        });
        initData();
    }

    private void initData(){
        String url = String.format(Api.GUOKR_ARTICLE_BASE_URL, id);
        Callback callback = new Callback(){
            @Override
            public void onFailure(Call call, IOException e){
                Toast.makeText(GuokrDetailActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                if(response != null && response.isSuccessful()){
                    String json = response.body().string();
                    try{
                        JSONObject jsonObject = new JSONObject(json);
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        JSONObject object = jsonArray.getJSONObject(0);
                        String source_name = object.getString("source_name");
                        String title = object.getString("title");
                        String content = object.getString("content");
                        bean = new GuokerDetailBean(source_name, title, content);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    Message message = mHandler.obtainMessage();
                    message.obj = bean;
                    message.what = 0;
                    mHandler.sendMessage(message);
                }
            }
        };
        OkHttpUtils.getData(url, callback);
    }

    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.guoker_toobar);
        setSupportActionBar(toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.guokr_col_layout);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.guokr_fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivheadline = (ImageView) findViewById(R.id.guokr_iv_img);
        content = (TextView) findViewById(R.id.guokr_Textview);
        guok_name = (TextView) findViewById(R.id.guokr_name);
        guok_title = (TextView) findViewById(R.id.guokr_title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
