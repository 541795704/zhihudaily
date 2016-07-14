package com.project.zhihudaily.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.zhihudaily.Adapter.VedioAdpter;
import com.project.zhihudaily.Bean.Vedio;
import com.project.zhihudaily.R;
import com.project.zhihudaily.Utils.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 2016/7/5.
 */
public class VedioFragment extends android.support.v4.app.Fragment{

    private ListView listView;
    private RequestQueue queue;
    private VedioAdpter vedioAdpter;
    private List<Vedio> vedioList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_vedio, container, false);
        listView = (ListView) view.findViewById(R.id.listview_video);
        initData();
        vedioAdpter = new VedioAdpter(vedioList, getActivity());
        listView.setAdapter(vedioAdpter);
        return view;
    }

    private void initData(){
        JSONObject jsonObject = new JSONObject();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Api.VEDIO, jsonObject, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject jsonObject){
                try{
                    JSONArray jsonArray = jsonObject.getJSONArray("list");
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject data = jsonArray.getJSONObject(i);
                        String text = data.getString("text");
                        String passtime = data.getString("passtime");
                        JSONObject video = data.getJSONObject("video");
                        int height = video.getInt("height");
                        int width = video.getInt("width");
                        String videoUrl = video.getJSONArray("video").get(0).toString();
                        String cover = video.getJSONArray("thumbnail").get(0).toString();
                        Vedio vedio = new Vedio(text, passtime, videoUrl, cover, height, width);
                        vedioList.add(vedio);
                    }
                    //更新数据
                    vedioAdpter.notifyDataSetChanged();
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
            }
        });
        //添加到队列
        queue.add(request);
    }
  //stop时关闭视频
    @Override
    public void onStop(){
        super.onStop();
        vedioAdpter.stop();
    }
}

