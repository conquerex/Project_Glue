package com.hm.project_glue.main.home.data;

/**
 * Created by HM on 2016-12-16.
 */

public class Lastest_photos
{
    private Photo photo;

    public Photo getPhoto ()
    {
        return photo;
    }

    public void setPhoto (Photo photo)
    {
        this.photo = photo;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [photo = "+photo+"]";
    }
}
