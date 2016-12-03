package com.hm.project_glue.main.home;

/**
 * Created by HM on 2016-11-29.
 */

public class HomeModel {
    public int homeCardImage;
    public String homeCardTitle;

    public HomeModel(int homeCardImage, String homeCardTitle) {
        this.homeCardImage = homeCardImage;
        this.homeCardTitle = homeCardTitle;
    }

    public int getHomeCardImage() {
        return homeCardImage;
    }

    public void setHomeCardImage(int homeCardImage) {
        this.homeCardImage = homeCardImage;
    }

    public String getHomeCardTitle() {
        return homeCardTitle;
    }

    public void setHomeCardTitle(String homeCardTitle) {
        this.homeCardTitle = homeCardTitle;
    }

}
