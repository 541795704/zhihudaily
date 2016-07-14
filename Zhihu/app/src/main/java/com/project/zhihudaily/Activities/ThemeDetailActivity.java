package com.project.zhihudaily.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.zhihudaily.Bean.Collect;
import com.project.zhihudaily.R;
import com.project.zhihudaily.Utils.Api;
import com.project.zhihudaily.Utils.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Callback;

public class ThemeDetailActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private TextView content;
    private TextView title_detail;
    private CheckBox checkBox;
    private String title;
    private String url;
    private String id_Str;
    private String name = MainActivity.name;//用来判断是否已登录，用户名是否存在
    private int id;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            Spanned body_html = (Spanned) msg.obj;
            //把获取的body数据加载到控件
            content.setText(body_html);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_detail);
        ShareSDK.initSDK(this);//初始化分享
        initViews();
        initData();//下载json数据
    }

    private void initViews(){
        title_detail = (TextView) findViewById(R.id.title_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        //设置toolbar标题
        toolbar.setTitle("");
        content = (TextView) findViewById(R.id.content_detial);
        checkBox = (CheckBox) findViewById(R.id.details_collect_theme);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //获取上个fragment传递的数据
        Intent intent = getIntent();
        id_Str = intent.getStringExtra("id");
        title = intent.getStringExtra("title"); //标题
        //设置textview标题
        title_detail.setText(title);
        id = Integer.parseInt(id_Str);
        //根据id判断是否已经收藏
        if(MainActivity.collects.contains(id)){
            checkBox.setChecked(true);
        }
        //点击收藏或者取消收藏
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                if(name.equals("")){
                    Toast.makeText(ThemeDetailActivity.this, "未登录，请您登陆再收藏！", Toast.LENGTH_SHORT).show();
                }else{
                    if(!isChecked){
                        //取消收藏
                        delete();
                    }else{
                        //收藏
                        increase();
                    }
                }
            }
        });
    }

    private void initData(){
        //地址和传递过来的ID拼接
        url = String.format(Api.NEWS, id_Str);
        Callback callback = new Callback(){
            @Override
            public void onFailure(Call call, IOException e){
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException{
                if(response != null && response.isSuccessful()){
                    String json = response.body().string();
                    try{
                        JSONObject jsonObject = new JSONObject(json);
                        String body = jsonObject.getString("body");
                        //把body转为html类型的
                        Spanned spanned = Html.fromHtml(body);
                        Message message = Message.obtain();
                        message.obj = spanned;
                        handler.sendMessage(message);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        OkHttpUtils.getData(url, callback);
    }

    @Override
    //按toolbar的箭头返回上一页
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //点击分享
    public void share(View view){
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(Api.ZHIHU_DAILY_BASE_URL + id_Str);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title + "   " + Api.ZHIHU_DAILY_BASE_URL + id_Str);
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(Api.ZHIHU_DAILY_BASE_URL + id_Str);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(Api.ZHIHU_DAILY_BASE_URL + id_Str);
        // 启动分享GUI
        oks.show(getApplicationContext());
    }

    //     添加收藏
    public void increase(){
        Collect collect = new Collect();
        collect.setName(name);
        collect.setId(id);
        collect.setTitle(title);
        collect.save(this, new SaveListener(){
            @Override
            public void onSuccess(){
                Toast.makeText(ThemeDetailActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                MainActivity.collects.add(id);
            }

            @Override
            public void onFailure(int i, String s){
                Toast.makeText(ThemeDetailActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //     取消收藏
    public void delete(){
        BmobQuery<Collect> query = new BmobQuery<>();
        query.addWhereEqualTo("name", name);
        query.addWhereEqualTo("id", id);
        query.setLimit(50);
        //执行查询方法
        query.findObjects(this, new FindListener<Collect>(){
            @Override
            public void onSuccess(List<Collect> list){
                Collect gameScore = new Collect();
                gameScore.setObjectId(list.get(0).getObjectId());
                gameScore.delete(ThemeDetailActivity.this, new DeleteListener(){

                    @Override
                    public void onSuccess(){
                        Log.i("bmob", "删除成功");
                        for(int i = 0; i < MainActivity.collects.size(); i++){
                            if(MainActivity.collects.get(i) == id){
                                MainActivity.collects.remove(i);
                                Toast.makeText(ThemeDetailActivity.this, "收藏已取消", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg){
                        Log.i("bmob", "删除失败：" + msg);
                    }
                });
            }

            @Override
            public void onError(int i, String s){
            }
        });
    }
}
