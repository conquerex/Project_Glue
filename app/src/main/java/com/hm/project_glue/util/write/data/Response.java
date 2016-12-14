package com.hm.project_glue.util.write.data;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-15.
 */

public class Response
{
    private String group_name;

    private String id;

    private ArrayList<String> lastest_posts;

    private String group_image;

    private String post_count;

    private String user_count;

    private String last_updated;

    private ArrayList<String> members;

    private String master;

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

    public ArrayList<String>  getLastest_posts ()
    {
        return lastest_posts;
    }

    public void setLastest_posts (ArrayList<String>  lastest_posts)
    {
        this.lastest_posts = lastest_posts;
    }

    public String getGroup_image ()
    {
        return group_image;
    }

    public void setGroup_image (String group_image)
    {
        this.group_image = group_image;
    }

    public String getPost_count ()
    {
        return post_count;
    }

    public void setPost_count (String post_count)
    {
        this.post_count = post_count;
    }

    public String getUser_count ()
    {
        return user_count;
    }

    public void setUser_count (String user_count)
    {
        this.user_count = user_count;
    }

    public String getLast_updated ()
    {
        return last_updated;
    }

    public void setLast_updated (String last_updated)
    {
        this.last_updated = last_updated;
    }

    public ArrayList<String>  getMembers ()
    {
        return members;
    }

    public void setMembers (ArrayList<String>  members)
    {
        this.members = members;
    }

    public String getMaster ()
    {
        return master;
    }

    public void setMaster (String master)
    {
        this.master = master;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [group_name = "+group_name+", id = "+id+", lastest_posts = "+lastest_posts+", group_image = "+group_image+", post_count = "+post_count+", user_count = "+user_count+", last_updated = "+last_updated+", members = "+members+", master = "+master+"]";
    }

}
