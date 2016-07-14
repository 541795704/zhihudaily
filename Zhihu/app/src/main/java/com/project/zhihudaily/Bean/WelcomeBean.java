package com.project.zhihudaily.Bean;

/**
 * Created by tian on 2016/7/4.
 */
public class WelcomeBean{

    /**
     * text : Daniel Burka
     * img : https://pic3.zhimg.com/2f6c747aadc533b3bf79b77c20cbe3f2.jpg
     */

    private String text;
    private String img;

    public WelcomeBean(String text, String img){
        this.text = text;
        this.img = img;
    }

    public WelcomeBean(){
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
