package com.hm.project_glue.main.list.data;

import java.util.List;

/**
 * Created by HM on 2016-12-06.
 */



public class Photos
{
    private List<Photo> photos;

    private String pk;

    public List<Photo> getPhotos ()
    {
        return photos;
    }

    public void setPhotos (List<Photo> photos)
    {
        this.photos = photos;
    }

    public String getPk ()
    {
        return pk;
    }

    public void setPk (String pk)
    {
        this.pk = pk;
    }


}

