package com.hm.project_glue.main.list;

import com.hm.project_glue.main.list.data.PostData;

/**
 * Created by HM on 2016-11-29.
 */

public interface ListPresenter {
    void setView(ListPresenter.View view);
    void callHttp(PostData postData, String GroupId);
    public interface View {
        void dataChanged(PostData post);
    }
}
