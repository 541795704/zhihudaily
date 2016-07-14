package com.project.zhihudaily.Fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.zhihudaily.Adapter.ThemeAdapter;
import com.project.zhihudaily.Bean.Theme;
import com.project.zhihudaily.Parse.ParseJson;
import com.project.zhihudaily.R;
import com.project.zhihudaily.Utils.Api;
import com.project.zhihudaily.Utils.OkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by tian on 2016/7/5.
 */
public class ThemeFragment extends Fragment{

    private ViewPager viewpager_theme;
    private TabLayout tablayout_theme;
    private List<Theme> themeList;
    private ThemeAdapter themePagerAdapter;
    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch(msg.what){
                case 0:
                    themeList = (List<Theme>) msg.obj;
                    List<Fragment> fragmentList = new ArrayList<>();
                    for(int i = 0; i < themeList.size(); i++){
                        fragmentList.add(ThemePageFragment.newInstance(themeList.get(i).getId()));
                    }
                    themePagerAdapter = new ThemeAdapter(getActivity().getSupportFragmentManager(), themeList, fragmentList);
                    viewpager_theme.setAdapter(themePagerAdapter);
                    tablayout_theme.setupWithViewPager(viewpager_theme);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_theme, container, false);
        //初始化fragment_theme中的控件
        viewpager_theme = (ViewPager) view.findViewById(R.id.viewpager_theme);
        tablayout_theme = (TabLayout) view.findViewById(R.id.tablayout_theme);
        initData();//初始化数据
        return view;
    }

    private void initData(){
        Callback callback = new Callback(){
            @Override
            public void onFailure(Call call, IOException e){
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                if(response != null && response.isSuccessful()){
                    //获得下载的json数据
                    String json_theme = response.body().string();
                    //解析json数据
                    List<Theme> themes = ParseJson.parseJson2Theme(json_theme);
                    Message message = Message.obtain();
                    message.obj = themes;
                    message.what = 0;
                    handler.sendMessage(message);
                }
            }
        };
        OkHttpUtils.getData(Api.THEMES, callback);
    }
}
