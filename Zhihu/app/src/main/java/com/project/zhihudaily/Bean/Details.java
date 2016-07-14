package com.project.zhihudaily.Bean;

/**
 * Created by Administrator on 2016/7/7.
 */
public class Details{
    private String title;
    private String text;
    private String img;

    public Details(){
    }

    public Details(String title, String text, String img){
        this.title = title;
        this.text = text;
        this.img = img;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getImg(){
        return img;
    }

    public void setImg(String img){
        this.img = img;
    }
}
