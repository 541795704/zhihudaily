package com.project.zhihudaily.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.zhihudaily.Activities.DetailsActivity;
import com.project.zhihudaily.Adapter.HotAdapter;
import com.project.zhihudaily.Bean.HotNews;
import com.project.zhihudaily.Parse.ParseJson;
import com.project.zhihudaily.R;
import com.project.zhihudaily.Utils.Api;
import com.project.zhihudaily.Utils.OkHttpUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by tian on 2016/7/5.
 */
public class HostMessageFragment extends android.support.v4.app.Fragment{

    private RecyclerView recycler;
    private SwipeRefreshLayout swiper;
    private List<HotNews> list;
    private HotAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_hostmessage, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recycler = (RecyclerView) view.findViewById(R.id.hot_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        swiper = (SwipeRefreshLayout) view.findViewById(R.id.hot_swipe);
        initRecycler(view);
        initSwipeRefresh(view);
    }
    private void initRecycler(View view) {
        swiper.setRefreshing(true);
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                list = (List<HotNews>) msg.obj;
                adapter = new HotAdapter(list,getContext());
                //                adapter.notifyDataSetChanged();
                recycler.setAdapter(adapter);
                swiper.setRefreshing(false);
                adapter.setOnRecyclerItmClickListene(new HotAdapter.OnRecyclerItmClickListene(){
                    @Override
                    public void onItemClickListene(View v, int position){
                        Intent intent = new Intent(getContext(), DetailsActivity.class);
                        intent.putExtra("id", list.get(position).getId());
                        startActivity(intent);
                    }
                });
            }
        };
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String json = response.body().string().toString();

                    Message message = handler.obtainMessage();
                    message.obj = ParseJson.getJsonhot(json);
                    handler.sendMessage(message);
                }
            }
        };
        OkHttpUtils.getData(Api.HOT,callback);

        //        adapter.setOnRecyclerItmClickListene(new HomeAdapter.OnRecyclerItmClickListene(){
        //            @Override
        //            public void onItemClickListene(View v, int position){
        //                Intent intent = new Intent(getContext(), DetailsActivity.class);
        //                intent.putExtra("id",list.get(position).getId());
        //                startActivity(intent);
        //            }
        //        });
    }
    private void initSwipeRefresh(final View view) {
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initRecycler(view);
            }
        });
    }
}
