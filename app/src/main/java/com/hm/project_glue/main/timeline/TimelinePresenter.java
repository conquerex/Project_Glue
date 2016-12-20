package com.hm.project_glue.main.timeline;

import com.hm.project_glue.main.timeline.data.TimelineData;

/**
 * Created by HM on 2016-12-18.
 */

public interface TimelinePresenter {
    void setView(TimelinePresenter.View view);
    void callHttp(String page, boolean upDownCode);
    interface View {
        void setProgress(int code);
        void dataChanged(TimelineData res);
    }
}
