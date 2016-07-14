package com.project.zhihudaily.Bean;

/**
 * Created by Thinkpad on 2016/7/5.
 */
public class GuokerBean{
   /* "result":[
    {
        "link_v2_sync_img":"http://jingxuan.guokr.com/pick/v2/19072/sync/",
            "source_name":"吃货研究所",
            "video_url":"",
            "images":Array[15],
            "id":19072,
            "category":"nature",
            "style":"article",
            "title":"梅干、梅酒、梅子酱烧鹅，酸溜溜的梅子还可以这么吃~——迟来的芒种啖梅｜二十四食记（12）",
            "source_data":Object{...},
        "headline_img_tb":"http://i1.15yan.guokr.cn/1vt4c3g91x729w3i1388kpbktx08op0j.jpg!content",
            "link_v2":"http://jingxuan.guokr.com/pick/v2/19072/",
            "date_picked":1467676800,
            "is_top":false,
            "link":"http://jingxuan.guokr.com/pick/19072/",
            "headline_img":"http://i1.15yan.guokr.cn/1vt4c3g91x729w3i1388kpbktx08op0j.jpg!content",
            "replies_count":0,
            "page_source":"http://jingxuan.guokr.com/pick/19072/?ad=1",
            "author":"煎茶君",
            "summary":"文｜煎茶君 芒种时节，江南的梅子成熟了，所以有芒种吃梅的习惯。这一篇补上芒种食记。 梅子尝起来酸溜溜",
            "source":"group",
            "reply_root_id":743766,
            "date_created":1467358276
    },*/
    private String title;
    private String id;
    private String headline_img;

    public GuokerBean(String title, String id, String headline_img){
        this.title = title;
        this.id = id;
        this.headline_img = headline_img;
    }

    public GuokerBean(){
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getHeadline_img(){
        return headline_img;
    }

    public void setHeadline_img(String headline_img){
        this.headline_img = headline_img;
    }
}
