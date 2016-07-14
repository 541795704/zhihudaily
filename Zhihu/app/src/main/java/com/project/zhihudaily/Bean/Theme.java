package com.project.zhihudaily.Bean;

/**
 * Created by tian on 2016/7/5.
 */
public class Theme{
    private String thumbnail;
    private String description;
    private String id;
    private String name;

    public Theme(){
    }

    public Theme(String thumbnail, String description, String id, String name){
        this.thumbnail = thumbnail;
        this.description = description;
        this.id = id;
        this.name = name;
    }

    public String getThumbnail(){
        return thumbnail;
    }

    public void setThumbnail(String thumbnail){
        this.thumbnail = thumbnail;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
