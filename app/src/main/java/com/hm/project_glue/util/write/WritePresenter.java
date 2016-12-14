package com.hm.project_glue.util.write;


import com.hm.project_glue.util.write.data.GroupResponse;

import java.util.ArrayList;

/**
 * Created by HM on 2016-12-13.
 */

public interface WritePresenter{

    void setView(WritePresenter.View view);
    void httpPosting(ArrayList<String> photosDatas, String selectGroupId, String content);

    interface View {
        void writeResult(int Code);
        void groupChanged(String groupId, String groupName);
        void setGroupListChanged(ArrayList<GroupResponse> results);
    }


}
