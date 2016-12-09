package com.hm.project_glue.main.home;

import android.content.Context;

/**
 * Created by HM on 2016-11-29.
 */

public class HomeModel {
    private int homeCardImage;
    private String homeCardTitle;
    private Context context;

    // 2016.12.06
    public HomeModel(Context context) {
        this.context = context;
    }

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
