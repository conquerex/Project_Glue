package com.hm.project_glue.service.notification;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "TEST";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
       FirebaseInstanceId.getInstance().getToken();
    }

    public String getToken(){
        String token = FirebaseInstanceId.getInstance().getToken();
        return token;
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
