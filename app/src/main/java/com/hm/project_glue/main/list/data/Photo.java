package com.hm.project_glue.main.list.data;

/**
 * Created by HM on 2016-12-06.
 */

public class Photo
{
    private String thumbnail="";
    private String medium_thumbnail="";
    private String full_size="";
    private String small_thumbnail="";

    public Photo(){

    }
    public String getThumbnail ()
    {
        return thumbnail;
    }

    public void setThumbnail (String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getMedium_thumbnail ()
    {
        return medium_thumbnail;
    }

    public void setMedium_thumbnail (String medium_thumbnail)
    {
        this.medium_thumbnail = medium_thumbnail;
    }

    public String getFull_size ()
    {
        return full_size;
    }

    public void setFull_size (String full_size)
    {
        this.full_size = full_size;
    }

    public String getSmall_thumbnail ()
    {
        return small_thumbnail;
    }

    public void setSmall_thumbnail (String small_thumbnail)
    {
        this.small_thumbnail = small_thumbnail;
    }

}
