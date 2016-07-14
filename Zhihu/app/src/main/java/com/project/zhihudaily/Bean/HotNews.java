package com.project.zhihudaily.Bean;

/**
 * Created by Administrator on 2016/7/6.
 */
public class HotNews{
    private String url;
    private String img;
    private String title;
    private int id;

    public HotNews(){
    }

    public HotNews(String url, String imgurl, String title,int id){
        this.url = url;
        this.img = imgurl;
        this.title = title;
        this.id = id;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getImg(){
        return img;
    }

    public void setImg(String imgurl){
        this.img = imgurl;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
