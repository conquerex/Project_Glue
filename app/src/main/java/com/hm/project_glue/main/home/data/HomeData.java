package com.hm.project_glue.main.home.data;

import java.util.ArrayList;

/**
 * Created by jongkook on 2016. 12. 7..
 */
public class HomeData
{
    private ArrayList<Response> Response;

    public ArrayList<Response> getResponse ()
    {
        return Response;
    }

    public void setResponse (ArrayList<Response> Response)
    {
        this.Response = Response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Response = "+Response+"]";
    }
}