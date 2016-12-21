package com.hm.project_glue.main.list.data;

/**
 * Created by HM on 2016-12-21.
 */

public class NotiJson {

        boolean post_noti ;
        boolean comment_noti ;
        boolean like_noti ;

        public NotiJson(boolean post, boolean comments,boolean like) {
            this.post_noti = post;
            this.comment_noti = comments;
            this.like_noti = like;
        }

}
