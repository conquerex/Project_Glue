package com.hm.project_glue.main.timeline.data;

/**
 * Created by HM on 2016-12-19.
 */

public class Dislike
{
    private String post;

    private User user;

    public String getPost ()
    {
        return post;
    }

    public void setPost (String post)
    {
        this.post = post;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [post = "+post+", user = "+user+"]";
    }
}

