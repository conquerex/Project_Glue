package com.hm.project_glue.main.timeline.data;

/**
 * Created by HM on 2016-12-18.
 */

public class Photo
{
    private String thumbnail;

    private String full_size;

    public String getThumbnail ()
    {
        return thumbnail;
    }

    public void setThumbnail (String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getFull_size ()
    {
        return full_size;
    }

    public void setFull_size (String full_size)
    {
        this.full_size = full_size;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [thumbnail = "+thumbnail+", full_size = "+full_size+"]";
    }
}