package com.project.zhihudaily.Bean;

/**
 * Created by Thinkpad on 2016/7/7.
 */
public class GuokerDetailBean{

   /* "source_name":"谣言粉碎机",
            "title":"胡萝卜一定要用很多油来炒吗？",
            "content"*/

    private String name;
    private String title;
    private String  content;

    public GuokerDetailBean(String name, String title, String content){
        this.name = name;
        this.title = title;
        this.content = content;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
