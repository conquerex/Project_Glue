package com.hm.project_glue.main.list.data;

import java.util.List;

/**
* Created by HM on 2016-12-03.
*/
public class PostData
{
    private List<Results> results;
    private String previous;
    private String count;
    private String next;
    public List<Results> getResults ()
    {
        return results;
    }

    public void setResults (List<Results> results)
    {
        this.results = results;
    }

    public String getPrevious ()
    {
        return previous;
    }

    public void setPrevious (String previous)
    {
        this.previous = previous;
    }

    public String getCount ()
    {
        return count;
    }

    public void setCount (String count)
    {
        this.count = count;
    }

    public String getNext ()
{
    return next;
}

    public void setNext (String next)
    {
        this.next = next;
    }





    public class Photos
    {
        private Photo photo;

        private String pk;

        public Photo getPhoto ()
        {
            return photo;
        }

        public void setPhoto (Photo photo)
        {
            this.photo = photo;
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
            return "ClassPojo [photo = "+photo+", pk = "+pk+"]";
        }
    }
    public class Photo
    {
        private String thumbnail;

        private String medium_thumbnail;

        private String full_size;

        private String small_thumbnail;

        public String getThumbnail ()
        {
            return thumbnail;
        }

        public void setThumbnail (String thumbnail)
        {
            this.thumbnail = thumbnail;
        }

        public String getMedium_thumbnail ()
        {
            return medium_thumbnail;
        }

        public void setMedium_thumbnail (String medium_thumbnail)
        {
            this.medium_thumbnail = medium_thumbnail;
        }

        public String getFull_size ()
        {
            return full_size;
        }

        public void setFull_size (String full_size)
        {
            this.full_size = full_size;
        }

        public String getSmall_thumbnail ()
        {
            return small_thumbnail;
        }

        public void setSmall_thumbnail (String small_thumbnail)
        {
            this.small_thumbnail = small_thumbnail;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [thumbnail = "+thumbnail+", medium_thumbnail = "+medium_thumbnail+", full_size = "+full_size+", small_thumbnail = "+small_thumbnail+"]";
        }
    }



}



