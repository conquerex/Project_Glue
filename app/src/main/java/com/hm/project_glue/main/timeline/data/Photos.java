package com.hm.project_glue.main.timeline.data;

/**
 * Created by HM on 2016-12-06.
 */


public class Photos
{

    Photo photo;
    String pk ="";

    public Photos(){

    }
    public Photo getPhoto ()
    {
        return photo;
    }

    public void setPhotos (Photo photo)
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


}