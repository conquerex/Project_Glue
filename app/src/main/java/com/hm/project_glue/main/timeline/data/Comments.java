package com.hm.project_glue.main.timeline.data;

/**
 * Created by HM on 2016-12-20.
 */

public class Comments
{
    private String content;

    private String created_date;

    private User user;

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getCreated_date ()
    {
        return created_date;
    }

    public void setCreated_date (String created_date)
    {
        this.created_date = created_date;
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
        return "ClassPojo [content = "+content+", created_date = "+created_date+", user = "+user+"]";
    }
}
