package com.hm.project_glue.main.list.data;

/**
 * Created by HM on 2016-12-19.
 */

public class Like
{
    private String post;

    private String is_dislike;

    private String like_count;

    private String is_like;

    private String dislike_count;

    private LikeUser user;

    public String getPost ()
    {
        return post;
    }

    public void setPost (String post)
    {
        this.post = post;
    }

    public String getIs_dislike ()
    {
        return is_dislike;
    }

    public void setIs_dislike (String is_dislike)
    {
        this.is_dislike = is_dislike;
    }

    public String getLike_count ()
    {
        return like_count;
    }

    public void setLike_count (String like_count)
    {
        this.like_count = like_count;
    }

    public String getIs_like ()
    {
        return is_like;
    }

    public void setIs_like (String is_like)
    {
        this.is_like = is_like;
    }

    public String getDislike_count ()
    {
        return dislike_count;
    }

    public void setDislike_count (String dislike_count)
    {
        this.dislike_count = dislike_count;
    }

    public LikeUser getUser ()
    {
        return user;
    }

    public void setUser (LikeUser user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [post = "+post+", is_dislike = "+is_dislike+", like_count = "+like_count+", is_like = "+is_like+", dislike_count = "+dislike_count+", user = "+user+"]";
    }
}
