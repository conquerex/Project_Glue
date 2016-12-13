package com.hm.project_glue.util.write.data;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-11.
 */

public class GroupListData {


    ArrayList<GroupResponse> Response;

    public ArrayList<GroupResponse>  getResults ()
    {
        return Response;
    }

    public void setResults (ArrayList<GroupResponse>  results)
    {
        this.Response = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [results = "+Response+"]";
    }

}
