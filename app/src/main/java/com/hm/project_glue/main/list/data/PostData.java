package com.hm.project_glue.main.list.data;

import java.util.ArrayList;

/**
* Created by HM on 2016-12-03.
*/
public class PostData
{
    ArrayList<Results> results;
    String previous;
    String count;
    String next;

    public PostData(){
    }

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

}







