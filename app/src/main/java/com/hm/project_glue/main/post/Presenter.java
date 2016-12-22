package com.hm.project_glue.main.post;

import com.hm.project_glue.main.list.data.Posts;

/**
 * Created by HM on 2016-12-22.
 */

public interface Presenter {

    void setView(Presenter.View view);
    public interface View {
        void dataSet(Posts res);
        void commentChanged();
    }
}
