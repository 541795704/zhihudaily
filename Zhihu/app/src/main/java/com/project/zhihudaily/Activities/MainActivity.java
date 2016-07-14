package com.project.zhihudaily.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.project.zhihudaily.Bean.Collect;
import com.project.zhihudaily.Fragment.CollectFragment;
import com.project.zhihudaily.Fragment.FanfouFragment;
import com.project.zhihudaily.Fragment.GuokrFragment;
import com.project.zhihudaily.Fragment.HomeFragment;
import com.project.zhihudaily.Fragment.HostMessageFragment;
import com.project.zhihudaily.Fragment.ThemeFragment;
import com.project.zhihudaily.Fragment.VedioFragment;
import com.project.zhihudaily.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private HomeFragment homeFragment;
    private ThemeFragment themeFragment;
    private HostMessageFragment hostMessageFragment;
    private FanfouFragment fanfouFragment;
    private GuokrFragment guokrFragment;
    private VedioFragment vedioFragment;
    private CollectFragment aboutFragment;
    private int mBackKeyPressedTimes = 0;

    //记录收藏
    public static List<Integer> collects = new ArrayList<>();
    //用户id
    public static String id = "";
    //用户名
    public static String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initView();
    }

    private void initView(){
        homeFragment = new HomeFragment();
        themeFragment = new ThemeFragment();
        hostMessageFragment = new HostMessageFragment();
        fanfouFragment = new FanfouFragment();
        guokrFragment = new GuokrFragment();
        vedioFragment = new VedioFragment();
        aboutFragment = new CollectFragment();
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        //打开应用默认显示的界面(homefragment)
        HomeFragment fragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        //监听侧拉栏，点击显示不同的fragment
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem item){
                switch(item.getItemId()){
                    case R.id.home_nav:
                        changeFragment(homeFragment);
                        toolbar.setTitle(item.getTitle());
                        break;
                    case R.id.theme_nav:
                        changeFragment(themeFragment);
                        toolbar.setTitle(item.getTitle());
                        break;
                    case R.id.hot_nav:
                        changeFragment(hostMessageFragment);
                        toolbar.setTitle(item.getTitle());
                        break;
                    case R.id.fangou_nav:
                        changeFragment(fanfouFragment);
                        toolbar.setTitle(item.getTitle());
                        break;
                    case R.id.guokr_nav:
                        changeFragment(guokrFragment);
                        toolbar.setTitle(item.getTitle());
                        break;
                    case R.id.vedio_nav:
                        changeFragment(vedioFragment);
                        toolbar.setTitle(item.getTitle());
                        break;
                    case R.id.collect:
                        changeFragment(aboutFragment);
                        toolbar.setTitle(item.getTitle());
                        break;
                }
                //关闭抽屉
                drawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        });
    }

    //初始化toobar
    private void initToolbar(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置和侧拉栏关联
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }

    //切换fragment
    private void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    //按返回键关闭抽屉
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(Gravity.LEFT)){
            drawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }else if(!drawerLayout.isDrawerOpen(Gravity.LEFT) && mBackKeyPressedTimes == 0){//按两次退出app
            Toast.makeText(this, "再按一次退出程序 ", Toast.LENGTH_SHORT).show();
            mBackKeyPressedTimes = 1;//改变时间
            //开启一个子线程睡两秒，两秒内无继续操作就不退出
            new Thread(){
                @Override
                public void run(){
                    try{
                        Thread.sleep(2000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }finally{
                        mBackKeyPressedTimes = 0;
                    }
                }
            }.start();
            return;
        }else{
            this.finish();
        }
        super.onBackPressed();
    }
   //登陆
    public void login(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, 1);
    }
     //登陆和注册返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        TextView login_text = (TextView) findViewById(R.id.login);
        if(requestCode == 1 && resultCode == 0){
        }else{
            if(requestCode == 1 && resultCode == 1){
                Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            }else if(requestCode == 1 && resultCode == 2){
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            }
            login_text.setText(data.getStringExtra("name"));
            name = data.getStringExtra("name");
            collects.clear();
            query();//获取收藏
        }
    }

    /**
     * 获取收藏记录
     */
    public void query(){
        BmobQuery<Collect> query = new BmobQuery<>();
        query.addWhereEqualTo("name", name);
        query.setLimit(100);
        //执行查询方法
        query.findObjects(MainActivity.this, new FindListener<Collect>(){
            @Override
            public void onSuccess(List<Collect> list){
                for(int i = 0; i < list.size(); i++){
                    collects.add(list.get(i).getId());
                }
            }

            @Override
            public void onError(int i, String s){
            }
        });
    }
}
