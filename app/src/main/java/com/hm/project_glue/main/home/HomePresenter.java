package com.hm.project_glue.main.home;

/**
 * Created by HM on 2016-11-29.
 */

public interface HomePresenter {
    void setView(HomePresenter.View view);
    void callHttp();

    // setView로 부르기 위해 임시로 만든 View
    public interface View {

    }


}
