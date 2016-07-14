package com.project.zhihudaily.Bean;

/**
 * Created by tian on 2016/7/7.
 */
public class Vedio{
    private String text;
    private String passtime;
    private String videoUrl;
    private String cover;
    private int height;
    private int width;

    public Vedio(){
    }

    public Vedio(String text, String passtime, String videoUrl, String cover, int height, int width){
        this.text = text;
        this.passtime = passtime;
        this.videoUrl = videoUrl;
        this.cover = cover;
        this.height = height;
        this.width = width;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getPasstime(){
        return passtime;
    }

    public void setPasstime(String passtime){
        this.passtime = passtime;
    }

    public String getVideoUrl(){
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl){
        this.videoUrl = videoUrl;
    }

    public String getCover(){
        return cover;
    }

    public void setCover(String cover){
        this.cover = cover;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getWidth(){
        return width;
    }

    public void setWidth(int width){
        this.width = width;
    }
}
