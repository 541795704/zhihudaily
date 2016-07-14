package com.project.zhihudaily.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.zhihudaily.Activities.GuokrDetailActivity;
import com.project.zhihudaily.Adapter.GuokerAdapter;
import com.project.zhihudaily.Bean.GuokerBean;
import com.project.zhihudaily.R;
import com.project.zhihudaily.Utils.Api;
import com.project.zhihudaily.Utils.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by tian on 2016/7/5.
 */
public class GuokrFragment extends android.support.v4.app.Fragment{

    private SwipeRefreshLayout srlGuokr;//下拉涮新
    private RecyclerView recyclerView;
    private List<GuokerBean> list;
    private GuokerAdapter adapter;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 0:
                    list = (List<GuokerBean>) msg.obj;
                    adapter = new GuokerAdapter(list, getActivity());
                    recyclerView.setAdapter(adapter);
                    srlGuokr.post(new Runnable(){
                        @Override
                        public void run(){
                            srlGuokr.setRefreshing(false);
                        }
                    });
                    //接口回调,给recyclerView每个item设置监听
                    adapter.setOnItemClickListener(new GuokerAdapter.OnItemClickListener(){
                        @Override
                        public void onItemClick(int position){
                            GuokerBean guokerBean = list.get(position);
                            Intent intent=new Intent(getActivity(), GuokrDetailActivity.class);
                            intent.putExtra("id",guokerBean.getId());
                            intent.putExtra("title",guokerBean.getTitle());
                            intent.putExtra("headline_img_tb",guokerBean.getHeadline_img());
                            startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_guokr, container, false);
        initView(view);
        initData();
        //涮新页面
        srlGuokr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh(){
                if(!list.isEmpty()){
                    list.clear();
                }
                adapter.notifyDataSetChanged();
                initData();
            }
        });
        return view;
    }

    private void initView(View view){
        srlGuokr = (SwipeRefreshLayout) view.findViewById(R.id.guokr_Swipe_refresh);
        recyclerView = (RecyclerView) view.findViewById(R.id.guokr_recyclerview);
        //给RecyclerViews设置布局管理器
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //给recyclerView 设置属性
        //设置下拉刷新的按钮的颜色
        srlGuokr.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_red_light);
        //设置手指在屏幕上下拉多少距离开始刷新
        srlGuokr.setDistanceToTriggerSync(200);
        //设置下拉刷新按钮的背景颜色
        srlGuokr.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置下拉刷新按钮的大小
        srlGuokr.setSize(SwipeRefreshLayout.DEFAULT);
    }

    /**
     * 下载，解析数据
     */
    private void initData(){
        //下载之前，刷新页面
        srlGuokr.post(new Runnable(){
            @Override
            public void run(){
                srlGuokr.setRefreshing(true);
            }
        });
        Callback callback = new Callback(){
            @Override
            public void onFailure(Call call, IOException e){
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                if(response != null && response.isSuccessful()){
                    list = new ArrayList<>();
                    String json = response.body().string();
                    GuokerBean guokerBean = null;
                    try{
                        JSONObject object = new JSONObject(json);
                        JSONArray array = object.getJSONArray("result");
                        for(int i = 0; i < array.length(); i++){
                            JSONObject data = array.getJSONObject(i);
                            String id = data.getString("id");
                            String title = data.getString("title");
                            String headline_img_tb = data.getString("headline_img_tb");
                            guokerBean = new GuokerBean(title, id, headline_img_tb);
                            list.add(guokerBean);
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    Message message = mHandler.obtainMessage();
                    message.obj = list;
                    message.what = 0;
                    mHandler.sendMessage(message);
                }
            }
        };
        OkHttpUtils.getData(Api.GUOKR_ARTICLES, callback);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(srlGuokr.isRefreshing()){
            srlGuokr.setRefreshing(false);
        }
    }
}
