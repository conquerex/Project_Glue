package com.hm.project_glue.main.timeline.data;


import java.util.ArrayList;

/**
 * Created by HM on 2016-12-18.
 */

public class TimelineData
{
    private ArrayList<Results> results;

    private String previous;

    private String count;

    private String next;

    public ArrayList<Results> getResults ()
    {
        return results;
    }

    public void setResults (ArrayList<Results> results)
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

    @Override
    public String toString()
    {
        return "ClassPojo [results = "+results+", previous = "+previous+", count = "+count+", next = "+next+"]";
    }
}
