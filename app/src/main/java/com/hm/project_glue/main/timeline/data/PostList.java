package com.hm.project_glue.main.timeline.data;

/**
 * Created by HM on 2016-12-18.
 */

public class PostList
{
    private String created_date;

    private Posts posts;

    public String getCreated_date ()
    {
        return created_date;
    }

    public void setCreated_date (String created_date)
    {
        this.created_date = created_date;
    }

    public Posts getPosts ()
    {
        return posts;
    }

    public void setPosts (Posts posts)
    {
        this.posts = posts;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [created_date = "+created_date+", posts = "+posts+"]";
    }
}
