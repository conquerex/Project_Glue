package com.hm.project_glue.util.write.data;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-11.
 */

public class GroupListData {

    ArrayList<GroupListResults> results;

    public ArrayList<GroupListResults>  getResults ()
    {
        return results;
    }

    public void setResults (ArrayList<GroupListResults>  results)
    {
        this.results = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [results = "+results+"]";
    }



}
