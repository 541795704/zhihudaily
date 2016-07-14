package com.project.zhihudaily.Bean;

/**
 * Created by tian on 2016/7/5.
 */
public class ThemeContent {
    private String id;
    private String[] images;
    private String title;

    public ThemeContent(String id,String[] images,String title){
        this.id = id;
        this.images = images;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String[] getImages() {

        if (images == null)
            return null;
        return images;
    }

    public String getFirstImg(){
        if (images == null)
            return null;
        return images[0];
    }
}
