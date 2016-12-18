package com.hm.project_glue.main.list.timeline.data;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-18.
 */

public class Posts
{
    private String likes_count;

    private ArrayList<Photos> photos;

    private String content;

    private ArrayList<String> dislike;

    private ArrayList<String> like;

    private String group;

    private User user;

    private ArrayList<String> comments;

    private String dislikes_count;

    private String pk;

    public String getLikes_count ()
    {
        return likes_count;
    }

    public void setLikes_count (String likes_count)
    {
        this.likes_count = likes_count;
    }

    public ArrayList<Photos> getPhotos ()
    {
        return photos;
    }

    public void setPhotos (ArrayList<Photos> photos)
    {
        this.photos = photos;
    }

    public String getContent ()
    {
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public ArrayList<String> getDislike ()
    {
        return dislike;
    }

    public void setDislike (ArrayList<String> dislike)
    {
        this.dislike = dislike;
    }

    public ArrayList<String> getLike ()
    {
        return like;
    }

    public void setLike (ArrayList<String> like)
    {
        this.like = like;
    }

    public String getGroup ()
    {
        return group;
    }

    public void setGroup (String group)
    {
        this.group = group;
    }

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
    }

    public ArrayList<String> getComments ()
    {
        return comments;
    }

    public void setComments (ArrayList<String> comments)
    {
        this.comments = comments;
    }

    public String getDislikes_count ()
    {
        return dislikes_count;
    }

    public void setDislikes_count (String dislikes_count)
    {
        this.dislikes_count = dislikes_count;
    }

    public String getPk ()
    {
        return pk;
    }

    public void setPk (String pk)
    {
        this.pk = pk;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [likes_count = "+likes_count+", photos = "+photos+", content = "+content+", dislike = "+dislike+", like = "+like+", group = "+group+", user = "+user+", comments = "+comments+", dislikes_count = "+dislikes_count+", pk = "+pk+"]";
    }
}