package com.hm.project_glue.main.list.timeline.data;

/**
 * Created by HM on 2016-12-18.
 */

public class User
{
    private String name;

    private String image;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getImage ()
    {
        return image;
    }

    public void setImage (String image)
    {
        this.image = image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", image = "+image+"]";
    }
}

