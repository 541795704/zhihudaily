package com.project.zhihudaily.Parse;

import com.project.zhihudaily.Bean.Details;
import com.project.zhihudaily.Bean.HotNews;
import com.project.zhihudaily.Bean.News;
import com.project.zhihudaily.Bean.Theme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tian on 2016/7/5.
 */
public class ParseJson{

    public static List<Theme> parseJson2Theme(String json){
        try{
            List<Theme> themeList = new ArrayList<>();
            JSONObject data = new JSONObject(json);
            JSONArray others = data.getJSONArray("others");
            for(int i = 0; i < others.length(); i++){
                JSONObject jsonObject = others.getJSONObject(i);
                String thumbnail = jsonObject.getString("thumbnail");
                String description = jsonObject.getString("description");
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                Theme theme = new Theme(thumbnail, description, id, name);
                themeList.add(theme);
            }
            return themeList;
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<News> getJson2Home(String json){
        List<News> list = new ArrayList<>();
        try{
            JSONObject object = new JSONObject(json);
            JSONArray stories = object.getJSONArray("stories");
            for(int i = 0; i < stories.length(); i++){
                JSONObject jsonObject = stories.getJSONObject(i);
                String imgurl = jsonObject.getJSONArray("images").getString(0);
                String title = jsonObject.getString("title");
                int id = jsonObject.getInt("id");
                list.add(new News(imgurl, title, id));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return list;
    }

    public static List<HotNews> getJsonhot(String json){
        List<HotNews> list = new ArrayList<>();
        try{
            JSONObject object = new JSONObject(json);
            JSONArray recent = object.getJSONArray("recent");
            for(int i = 0; i < recent.length(); i++){
                JSONObject jsonObject = recent.getJSONObject(i);
                String imgurl = jsonObject.getString("thumbnail");
                String title = jsonObject.getString("title");
                String url = jsonObject.getString("url");
                int id = jsonObject.getInt("news_id");
                list.add(new HotNews(url, imgurl, title, id));
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
        return list;
    }
    public static List<Details> getJsonDetails(String json){
        List<Details> list = new ArrayList<>();
        try{
            JSONObject object = new JSONObject(json);
                String imgurl = object.getString("image");
                String title = object.getString("title");
                String text = object.getString("body");
                list.add(new Details(title, text,imgurl));
        }catch(JSONException e){
            e.printStackTrace();
        }
        return list;
    }
}
