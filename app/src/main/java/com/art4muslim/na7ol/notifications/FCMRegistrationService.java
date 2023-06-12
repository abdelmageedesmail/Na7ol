package com.art4muslim.na7ol.notifications;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.art4muslim.na7ol.internet.Urls;
import com.art4muslim.na7ol.utils.PrefrencesStorage;
import com.google.firebase.iid.FirebaseInstanceId;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONObject;


/**
 * Created by abdelmageed on 30/03/17.
 */

public class FCMRegistrationService extends IntentService {

    Context context;
    SharedPreferences sh;
    String id, userType;

    public static String success, token;
    private String api_token;
    private String user_id, macAddress;
    private String userToken;
    private PrefrencesStorage localeShared;

    public FCMRegistrationService() {
        super("FCM");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        localeShared = new PrefrencesStorage(this);
        user_id = localeShared.getId();
        userToken = localeShared.getToken();
        token = FirebaseInstanceId.getInstance().getToken();
        Log.e("token", "" + token);
        localeShared.storeKey("oldToken", token);
//        FCMTokenRefreshListenerService.refreshed=true;
//        if (FCMTokenRefreshListenerService.refreshed){
        refreshToken();
//        }
    }

    private void refreshToken() {

        AndroidNetworking.initialize(this);
        AndroidNetworking.setParserFactory(new JacksonParserFactory());
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.STORE_TOKEN);
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e");
        post.addBodyParameter("userId", user_id);
        post.addBodyParameter("playerId", token);
        post.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("response", "" + response);
                        localeShared.storeKey("oldToken", token);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }
}
