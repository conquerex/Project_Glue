package com.hm.project_glue.main.home.data;

import android.util.Log;
import java.util.ArrayList;

/**
 * Created by jongkook on 2016. 12. 7..
 */
public class HomeData {
    private static final String TAG = "HomeData";
    ArrayList<HomeResponse> homeResponses;

    public HomeData() {
        Log.i(TAG, "----------- HomeData");
    }

    public static HomeData newHomeInstance(){
        return new HomeData();
    }

    public static ArrayList<HomeResponse> newArrayListHomeInstance(){
        return new ArrayList<HomeResponse>();
    }

    public ArrayList<HomeResponse> getHomeResponses() {
        return homeResponses;
    }

    public void setHomeResponses(ArrayList<HomeResponse> homeResponses) {
        this.homeResponses = homeResponses;
    }
}
