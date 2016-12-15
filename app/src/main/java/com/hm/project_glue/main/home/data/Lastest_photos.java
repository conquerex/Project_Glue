package com.hm.project_glue.main.home.data;

/**
 * Created by jongkook on 2016. 12. 15..
 */

public class Lastest_photos {
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
