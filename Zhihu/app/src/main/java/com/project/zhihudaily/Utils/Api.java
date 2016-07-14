package com.project.zhihudaily.Utils;

/**
 * Created by tian on 2016/7/4.
 */
public class Api{

    // 主题日报列表查看(ThemeFragment)
    public static final String THEMES = "http://news-at.zhihu.com/api/4/themes";

    //主题日报的内容要和ID拼接(ThemePageFragment)
    public static final String THEME = "http://news-at.zhihu.com/api/4/theme/%s";

    // 获取果壳精选的文章列表,通过组合相应的参数成为完整的url
    public static final String GUOKR_ARTICLES = "http://apis.guokr.com/handpick/article.json?retrieve_type=by_since&category=all&limit=20&ad=1";
    public static final String ZUIXIN = "http://news-at.zhihu.com/api/4/news/latest";

    // 在最新消息中获取到的id，拼接到这个NEWS之后，可以获得对应的JSON格式的内容(detail)
    public static final String NEWS = "http://news-at.zhihu.com/api/4/news/%s";

    //将文章id拼接，显示的是webview网站
    public static final String ZHIHU_DAILY_BASE_URL = "http://news-at.zhihu.com/story/";

    // 热门消息
    public static final String HOT = "http://news-at.zhihu.com/api/3/news/hot";

    //视频
    public static final String VEDIO = "http://s.budejie.com/topic/list/jingxuan/41/budejie-android-6.4.0/0-20.json?market=taobao&ver=6.4.0&visiting=&os=6.0.1&appname=baisibudejie&client=android&udid=866646022322922&mac=02%3A00%3A00%3A00%3A00%3A00";

    // 饭否
    public static final String FANFOU_API = "http://blog.fanfou.com/digest/json/2016-07-06.daily.json";

    public static final String FANFOU_DAILY = "http://blog.fanfou.com/digest";

    // 获取果壳文章的具体信息
    public static final String GUOKR_ARTICLE_LINK_V1 = "http://jingxuan.guokr.com/pick/";

    // 获取果壳文章的具体信息
    public static final String GUOKR_ARTICLE_LINK_V2 = "http://jingxuan.guokr.com/pick/v2/";
    public static final String GUOKR_ARTICLE_BASE_URL = "http://apis.guokr.com/handpick/article.json?pick_id=%s";
}
