package com.hm.project_glue.main.list.data;

/**
 * Created by HM on 2016-12-19.
 */

public class LikeUser
{
    private String user_pk;

    private String name;

    private String image;

    public String getUser_pk ()
    {
        return user_pk;
    }

    public void setUser_pk (String user_pk)
    {
        this.user_pk = user_pk;
    }

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
        return "ClassPojo [user_pk = "+user_pk+", name = "+name+", image = "+image+"]";
    }
}
