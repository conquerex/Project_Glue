package com.hm.project_glue.util.write.data;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-11.
 */

public class GroupListData {


    ArrayList<Response> Response;

    public ArrayList<Response>  getResults ()
    {
        return Response;
    }

    public void setResults (ArrayList<Response>  results)
    {
        this.Response = results;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [results = "+Response+"]";
    }

}
