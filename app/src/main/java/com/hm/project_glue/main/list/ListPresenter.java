package com.hm.project_glue.main.list;

/**
 * Created by HM on 2016-11-29.
 */

public interface ListPresenter {
    void setView(ListPresenter.View view);
    void getPostJson(String GroupId);
    public interface View {

    }
}
