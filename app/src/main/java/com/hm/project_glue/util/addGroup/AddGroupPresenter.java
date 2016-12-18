package com.hm.project_glue.util.addGroup;

/**
 * Created by jongkook on 2016. 12. 19..
 */

public interface AddGroupPresenter {
    void setView(AddGroupPresenter.View view);
    void addHttp();

    // setView로 부르기 위해 임시로 만든 View
    public interface View {
        void progressAddGroupShow(boolean status);
        void addGroupResult(int code);
    }
}
