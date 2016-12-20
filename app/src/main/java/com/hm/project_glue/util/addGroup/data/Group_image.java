package com.hm.project_glue.util.addGroup.data;

/**
 * Created by jongkook on 2016. 12. 20..
 */

public class Group_image {
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
