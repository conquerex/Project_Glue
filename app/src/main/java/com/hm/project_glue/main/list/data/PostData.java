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








}



