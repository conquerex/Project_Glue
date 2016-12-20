package com.hm.project_glue.main.timeline.data;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-18.
 */

public class Results
{
    private ArrayList<PostList> PostList;

    public ArrayList<PostList> getPostList ()
    {
        return PostList;
    }

    public void setPostList (ArrayList<PostList> PostList)
    {
        this.PostList = PostList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [PostList = "+PostList+"]";
    }
}
