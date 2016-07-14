package com.project.zhihudaily.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.zhihudaily.Bean.Collect;
import com.project.zhihudaily.R;
import com.project.zhihudaily.Utils.Api;
import com.project.zhihudaily.Utils.OkHttpUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailsActivity extends AppCompatActivity{

    private TextView title_detail;
    private ImageView imageView;
    private Toolbar toolbar;
    private String title;

    private TextView content;
    private List<String> list = new ArrayList<>();
    private CheckBox checkBox;
    private int id;
    private String name = MainActivity.name;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        title_detail = (TextView) findViewById(R.id.title_detail);
        imageView = (ImageView) findViewById(R.id.img_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar_detail);
        checkBox = (CheckBox) findViewById(R.id.details_collect);
        //设置toolbar标题
        toolbar.setTitle("");
        content = (TextView) findViewById(R.id.content_detial);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        id = getIntent().getIntExtra("id", 0);
        if(MainActivity.collects.contains(id)){
            checkBox.setChecked(true);
        }
        initData();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                String _id = id + "";
                if(name.equals("")){
                    Toast.makeText(DetailsActivity.this, "未登录，请您登陆再收藏！", Toast.LENGTH_SHORT).show();
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
        //设置textview标题
        //title_detail.setText(title);
        //把获取的body数据加载到控件
        String url = String.format(Api.NEWS, id);
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                Picasso.with(DetailsActivity.this).load(list.get(0)).into(imageView);
                title_detail.setText(list.get(1));
                content.setText(Html.fromHtml(list.get(2)));
            }
        };
        Callback callback = new Callback(){

            @Override
            public void onFailure(Call call, IOException e){
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                if(response.isSuccessful()){
                    String json = response.body().string().toString();
                    try{
                        JSONObject object = new JSONObject(json);
                        String imgurl = object.getString("image");
                        title = object.getString("title");
                        String text = object.getString("body");
                        list.add(imgurl);
                        list.add(title);
                        list.add(text);
                        Message message = handler.obtainMessage();
                        message.obj = list;
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

    /**
     * 分享
     *
     * @param view
     */
    public void share(View view){
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(Api.ZHIHU_DAILY_BASE_URL + id);
        // text是分享文本，所有平台都需要这个字段
        oks.setText(title + "   " + Api.ZHIHU_DAILY_BASE_URL + id);
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(Api.ZHIHU_DAILY_BASE_URL + id);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment(title + "   " + Api.ZHIHU_DAILY_BASE_URL + id);
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(title + "   " + Api.ZHIHU_DAILY_BASE_URL + id);
        // 启动分享GUI
        oks.show(this);
    }

    /**
     * 添加收藏
     */
    public void increase(){
        Collect collect = new Collect();
        collect.setName(name);
        collect.setId(id);
        collect.setTitle(list.get(1));
        collect.save(this, new SaveListener(){
            @Override
            public void onSuccess(){
                Toast.makeText(DetailsActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                MainActivity.collects.add(id);
            }

            @Override
            public void onFailure(int i, String s){
                Toast.makeText(DetailsActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 取消收藏
     */
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
                gameScore.delete(DetailsActivity.this, new DeleteListener(){

                    @Override
                    public void onSuccess(){
                        // TODO Auto-generated method stub
                        Log.i("bmob", "删除成功");
                        for(int i = 0; i < MainActivity.collects.size(); i++){
                            if(MainActivity.collects.get(i) == id){
                                MainActivity.collects.remove(i);
                                Toast.makeText(DetailsActivity.this, "收藏已取消", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg){
                        // TODO Auto-generated method stub
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
