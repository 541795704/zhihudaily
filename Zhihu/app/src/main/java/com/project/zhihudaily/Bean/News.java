package com.project.zhihudaily.Bean;

/**
 * Created by Administrator on 16-7-5.
 */
public class News {
    private String imgurl;
    private String title;
    private int id;

    public News() {
    }

    public News(String imgurl, String title,int id) {
        this.imgurl = imgurl;
        this.title = title;
        this.id = id;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
