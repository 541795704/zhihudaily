package com.project.zhihudaily.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.project.zhihudaily.Activities.CollectDetailActivity;
import com.project.zhihudaily.Activities.MainActivity;
import com.project.zhihudaily.Adapter.CollectAdapter;
import com.project.zhihudaily.Bean.Collect;
import com.project.zhihudaily.R;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by tian on 2016/7/5.
 */
public class CollectFragment extends android.support.v4.app.Fragment{

    private ListView listview;
    private CollectAdapter collectAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_collect, container, false);
        listview = ((ListView) view.findViewById(R.id.listview_collect));
        //查询bmob云端的用户
        BmobQuery<Collect> query = new BmobQuery<>();
        query.addWhereEqualTo("name", MainActivity.name);
        //执行查询方法
        query.findObjects(getActivity(), new FindListener<Collect>(){
            @Override
            public void onSuccess(final List<Collect> list){
                //登陆后，获得对应用户收藏的id
                collectAdapter = new CollectAdapter(list, getContext());
                listview.setAdapter(collectAdapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                        Intent intent = new Intent(getActivity(), CollectDetailActivity.class);
                        //跳转到detail把ID传值过去
                        intent.putExtra("id", list.get(position).getId());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(int i, String s){
            }
        });
        return view;
    }
}
