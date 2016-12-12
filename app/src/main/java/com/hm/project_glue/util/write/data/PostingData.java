package com.hm.project_glue.util.write.data;


import java.util.Map;

import okhttp3.RequestBody;

/**
 * Created by HM on 2016-12-13.
 */

public class PostingData {
    String groupId;
    String content;
    String group;
    Map<String, RequestBody> partMap;
}
