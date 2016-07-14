package com.project.zhihudaily.Bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/7/8.
 */
public class Collect extends BmobObject{
    private String user_id;
    private String name;
    private int id;
    private String title;

    public Collect(){
    }

    public Collect(String user_id, String name, int id){
        this.user_id = user_id;
        this.name = name;
        this.id = id;
    }

    public Collect(String user_id, String name, int id, String title){
        this.user_id = user_id;
        this.name = name;
        this.id = id;
        this.title = title;
    }

    public String getUser_id(){
        return user_id;
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }
}
