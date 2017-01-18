package com.hm.project_glue.util.addGroup;

import android.graphics.Bitmap;

/**
 * Created by jongkook on 2016. 12. 19..
 */

public interface AddGroupPresenter {
    void setView(AddGroupPresenter.View view);
    Bitmap imgAddGroupReSizing(String path);
    void addGroupSave(Bitmap bitmap, String groupName);

    // setView로 부르기 위해 임시로 만든 View
    public interface View {
        void progressAddGroupShow(boolean status);
        void addGroupResult(int code);
        void addGroupFinish();
    }
}
