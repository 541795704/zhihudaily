package com.project.zhihudaily.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.project.zhihudaily.Activities.FanfouDetailActivity;
import com.project.zhihudaily.Adapter.FanfouAdapter;
import com.project.zhihudaily.Bean.FanfouBean;
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

/**
 * Created by tian on 2016/7/5.
 */
public class FanfouFragment extends android.support.v4.app.Fragment{

    private RecyclerView fanfou_recyclerView;
    private FanfouBean fanfouBean;
    private List<FanfouBean> list = new ArrayList<>();
    private FanfouAdapter fanfou_adapter;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 0:
                    list = (List<FanfouBean>) msg.obj;
                    //创建适配器
                    fanfou_adapter = new FanfouAdapter(getActivity(), list);
                    fanfou_recyclerView.setAdapter(fanfou_adapter);
                    fanfou_adapter.setOnItemClickListener(new FanfouAdapter.OnItemClickListener(){
                        @Override
                        public void onItemclick(int position){
                            FanfouBean fanfouBean = list.get(position);
                            Intent intent = new Intent(getContext(), FanfouDetailActivity.class);
                            intent.putExtra("realname", fanfouBean.getUsername());
                            intent.putExtra("avatar", fanfouBean.getAvatarurl());
                            intent.putExtra("time", fanfouBean.getTime());
                            intent.putExtra("msg", fanfouBean.getMsg());
                            getActivity().startActivity(intent);
                        }
                    });
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_fanfou, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData(){
        Callback callback = new Callback(){
            @Override
            public void onFailure(Call call, IOException e){
                Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException{
                if(response != null && response.isSuccessful()){
                    String json = response.body().string();
                    try{
                        JSONObject jsonObject = new JSONObject(json);
                        JSONArray msgs = jsonObject.getJSONArray("msgs");
                        for(int i = 0; i < msgs.length(); i++){
                            JSONObject msgsJSONObject = msgs.getJSONObject(i);
                            String realname = msgsJSONObject.getString("realname");
                            String loginname = msgsJSONObject.getString("loginname");
                            String avatar = msgsJSONObject.getString("avatar");
                            String time = msgsJSONObject.getString("time");
                            String msg = msgsJSONObject.getString("msg");
                            String preview = msgsJSONObject.getJSONObject("img").getString("preview").replace("/ff/m0/0c/", "/ff/n0/0c/");
                            fanfouBean = new FanfouBean(realname, loginname, avatar, time, msg, preview);
                            list.add(fanfouBean);
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
        OkHttpUtils.getData(Api.FANFOU_API, callback);
    }

    private void initView(View view){
        fanfou_recyclerView = (RecyclerView) view.findViewById(R.id.fanfou_recycle);
        //给RecyclerViews设置布局管理器
        fanfou_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


}
