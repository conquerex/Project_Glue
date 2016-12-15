package com.hm.project_glue.service.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
    private static String refreshedToken = "";
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        try{
            refreshedToken = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Refreshed token: " + refreshedToken);
            sendRegistrationToServer(refreshedToken);
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }

    }

    public String getToken(){

        return refreshedToken;
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
}
