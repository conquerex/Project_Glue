package com.hm.project_glue.main.timeline.data;

/**
 * Created by HM on 2016-12-18.
 */

public class Photos
{
    private Photo photo;

    private String pk;

    public Photo getPhoto ()
    {
        return photo;
    }

    public void setPhoto (Photo photo)
    {
        this.photo = photo;
    }

    public String getPk ()
    {
        return pk;
    }

    public void setPk (String pk)
    {
        this.pk = pk;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [photo = "+photo+", pk = "+pk+"]";
    }
}