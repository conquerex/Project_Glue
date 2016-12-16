package com.hm.project_glue.main.home.data;

/**
 * Created by HM on 2016-12-16.
 */

public class Group_image
{
    private String thumbnail;

    public String getThumbnail ()
    {
        return thumbnail;
    }

    public void setThumbnail (String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [thumbnail = "+thumbnail+"]";
    }
}
