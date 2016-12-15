package com.hm.project_glue.main.home.data;

/**
 * Created by jongkook on 2016. 12. 15..
 */

public class Lastest_posts {
    private String likes_count;

    private String content;

    private String uploaded_user;

    private Lastest_photos[] lastest_photos;

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

    public Lastest_photos[] getLastest_photos ()
    {
        return lastest_photos;
    }

    public void setLastest_photos (Lastest_photos[] lastest_photos)
    {
        this.lastest_photos = lastest_photos;
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
        return "ClassPojo [likes_count = "+likes_count+", content = "+content+", uploaded_user = "+uploaded_user+", lastest_photos = "+lastest_photos+", dislikes_count = "+dislikes_count+", pk = "+pk+"]";
    }
}
