package com.hm.project_glue.main.list.data;

import java.util.List;

/**
 * Created by HM on 2016-12-05.
 */

public class Results
{
    private String likes_count;
    private List<PostData.Photos> photos;
    private String content;
    private String uploaded_user;
    private String[] dislike;
    private String[] like;
    private String group;
    private String dislikes_count;
    private String pk;

    public Results(){

    }
    public String getLikes_count ()     {
        return likes_count;
    }

    public void setLikes_count (String likes_count)
    {
        this.likes_count = likes_count;
    }

    public List<PostData.Photos> getPhotos ()
    {
        return photos;
    }

    public void setPhotos (List<PostData.Photos> photos)
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
//
//        @Override
//        public String toString()
//        {
//            return "ClassPojo [likes_count = "+likes_count+", photos = "+photos+", content = "+content+", uploaded_user = "+uploaded_user+", dislike = "+dislike+", like = "+like+", group = "+group+", dislikes_count = "+dislikes_count+", pk = "+pk+"]";
//        }
}