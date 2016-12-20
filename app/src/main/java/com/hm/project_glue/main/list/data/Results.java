package com.hm.project_glue.main.list.data;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-08.
 */

public class Results
{
    String likes_count ="";
    ArrayList<Photos> photos;
    String content="";
    String uploaded_user="";
    User user;



    String[] dislike;
    String[] like;
    String group="";
    String dislikes_count="";
    String pk="";

    public Results(){

    }

    public User getUser() {    return user;    }

    public void setUser(User user) {        this.user = user;    }
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

    public String getUploaded_user ()
    {
        return uploaded_user;
    }

    public void setUploaded_user (String uploaded_user)
    {
        this.uploaded_user = uploaded_user;
    }

    public String[] getDislike ()
    {
        return dislike;
    }

    public void setDislike (String[] dislike)
    {
        this.dislike = dislike;
    }

    public String[] getLike ()
    {
        return like;
    }

    public void setLike (String[] like)
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

}