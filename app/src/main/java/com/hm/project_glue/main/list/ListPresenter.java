package com.hm.project_glue.main.list;

import com.hm.project_glue.main.timeline.data.PostData;

/**
 * Created by HM on 2016-11-29.
 */

public interface ListPresenter {
    void setView(ListPresenter.View view);
    void callHttp(String GroupId, String page, boolean upDownCode);
    interface View {
        void setProgress(int code);
        void dataChanged(PostData res);
    }
}
