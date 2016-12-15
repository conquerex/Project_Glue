package com.hm.project_glue.main.info.Data;

/**
 * Created by HM on 2016-12-15.
 */

public class My_group
{
    private String group_name;

    private String id;

    private String group_image;

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

    public String getGroup_image ()
{
    return group_image;
}

    public void setGroup_image (String group_image)
    {
        this.group_image = group_image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [group_name = "+group_name+", id = "+id+", group_image = "+group_image+"]";
    }
}