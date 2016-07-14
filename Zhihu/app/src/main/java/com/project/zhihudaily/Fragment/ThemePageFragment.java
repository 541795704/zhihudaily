package com.project.zhihudaily.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;
import com.project.zhihudaily.Activities.ThemeDetailActivity;
import com.project.zhihudaily.Adapter.RecyclerviewPagerAdapter;
import com.project.zhihudaily.Bean.ThemeContent;
import com.project.zhihudaily.R;
import com.project.zhihudaily.Utils.Api;
import com.project.zhihudaily.Utils.OkHttpUtils;
import com.squareup.picasso.Picasso;

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
public class ThemePageFragment extends Fragment{

    private ImageView img;
    private TextView tv_description;
    private List<ThemeContent> contentList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerViewHeader header;
    private RecyclerviewPagerAdapter pagerAdapter;
    private ProgressBar progressBar;

    //提供一个初始化ThemeBelowFragment的按方法
    public static ThemePageFragment newInstance(String id){
        ThemePageFragment themePageFragment = new ThemePageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        themePageFragment.setArguments(bundle);
        return themePageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_theme_page, container, false);
        initData();//初始化数据
        initView(view);//初始化控件
        return view;
    }

    private void initView(View view){
        img = (ImageView) view.findViewById(R.id.img_theme);
        tv_description = (TextView) view.findViewById(R.id.tv_theme_description);
        header = (RecyclerViewHeader) view.findViewById(R.id.header);
        progressBar = (ProgressBar) view.findViewById(R.id.progressbar_theme_page);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_theme_page);
        //recyclerView设置管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //给recyclerView添加头部，必须在设置管理器之后添加
        header.attachTo(recyclerView, true);
        //给recyclerView设置设配器
        pagerAdapter = new RecyclerviewPagerAdapter(contentList, getActivity());
        recyclerView.setAdapter(pagerAdapter);
        //设置recyclerView监听
        pagerAdapter.setOnItemClickListener(new RecyclerviewPagerAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(int position){
                //传数据显示不同的页面
                Intent intent = new Intent(getActivity(), ThemeDetailActivity.class);
                intent.putExtra("id", contentList.get(position).getId());
                intent.putExtra("title", contentList.get(position).getTitle());
                getActivity().startActivity(intent);
            }
        });
    }

    private void initData(){
        //获取id拼接到地址下载
        String uri = String.format(Api.THEME, getArguments().getString("id"));
        Callback callback = new Callback(){
            @Override
            public void onFailure(Call call, IOException e){
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException{
                if(response != null && response.isSuccessful()){
                    String json = response.body().string();
                    try{
                        JSONObject jsonObject = new JSONObject(json);
                        final String image = jsonObject.getString("image");
                        final String description = jsonObject.getString("description");
                        //解析内容
                        JSONArray stories = jsonObject.getJSONArray("stories");
                        for(int i = 0; i < stories.length(); i++){
                            JSONObject data = stories.getJSONObject(i);
                            String[] strings = null;
                            if(data.isNull("images")){
                                strings = null;
                            }else{
                                strings = new String[data.getJSONArray("images").length()];
                                JSONArray imgUrls = data.getJSONArray("images");
                                for(int j = 0; j < imgUrls.length(); j++){
                                    strings[j] = imgUrls.getString(j);
                                }
                            }
                            String title = data.getString("title");
                            String id = data.getString("id");
                            ThemeContent content = new ThemeContent(id, strings, title);
                            contentList.add(content);
                        }
                        getActivity().runOnUiThread(new Runnable(){
                            @Override
                            public void run(){
                                //设置头部图片和标题
                                Picasso.with(getActivity()).load(image).into(img);
                                tv_description.setText(description);
                                //跟新adapter数据
                                pagerAdapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        OkHttpUtils.getData(uri, callback);
    }
}


