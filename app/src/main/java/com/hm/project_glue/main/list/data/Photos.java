package com.hm.project_glue.main.list.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HM on 2016-12-06.
 */



public class Photos
{
    @SerializedName("photos")
    private Photo photo;
    @SerializedName("pk")
    private String pk ="";

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

