package com.hm.project_glue.main.timeline.data;

/**
 * Created by HM on 2016-12-21.
 */

public class Group
{
    private String group_name;

    private String id;

    public String getGroup_name ()
    {
        return group_name;
    }

    public void setGroup_name (String group_name)
    {
        this.group_name = group_name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [group_name = "+group_name+", id = "+id+"]";
    }
}