package com.project.zhihudaily.Bean;

/**
 * Created by Thinkpad on 2016/7/6.
 */
public class FanfouBean{
//
//    {
//        "id":"198034336",
//            "count":"27",
//            "realname":"王兴",
//            "loginname":"wangxing",
//            "avatar":"http://avatar.cdn.fanfou.com/s0/00/31/n3.jpg?1179311049",
//            "time":"2016-07-05 14:10",
//            "statusid":"6hRnoBiqNXw",
//            "msg":"越来越意识到，别说预测未来会发生什么了，99%的人连过去到底发生了什么都搞不清楚。",
//            "img":Object{...}


    private String username;
    private String loginname;
    private String avatarurl;
    private String time;
    private String msg;
    private String imgurl;

    public FanfouBean(String username, String loginname, String avatarurl, String time, String msg,String imgurl){
        this.username = username;
        this.loginname = loginname;
        this.avatarurl = avatarurl;
        this.time = time;
        this.msg = msg;
        this.imgurl = imgurl;
    }

    public FanfouBean(){
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getLoginname(){
        return loginname;
    }

    public void setLoginname(String loginname){
        this.loginname = loginname;
    }

    public String getAvatarurl(){
        return avatarurl;
    }

    public void setAvatarurl(String avatarurl){
        this.avatarurl = avatarurl;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

   public String getImgurl(){
        return imgurl;
    }

    public void setImgurl(String imgurl){
        this.imgurl = imgurl;
    }
}
