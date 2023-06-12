package com.art4muslim.na7ol.ui.notificationFragment;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.art4muslim.na7ol.internet.Urls;
import com.art4muslim.na7ol.internet.model.NotificationModel;
import com.art4muslim.na7ol.internet.model.ServerResponse;
import com.art4muslim.na7ol.utils.ProgressLoading;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

public class NotificationsPresenter {

    private NotificationsView view;

    public void setView(NotificationsView view) {
        this.view = view;
    }


    public void getNotifications(Context context, String userID) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_NOTIFICATIONS + Utils.getLang(context))
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("userId", userID)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            NotificationModel notificationModelResponse = new Gson().fromJson(response.toString(), new TypeToken<NotificationModel>() {
                            }.getType());
                            view.getNotifications(notificationModelResponse.getReturns());
                        } else {
                            view.getNotifications(null);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });

    }

//    public void delete(Context context, String user_id) {
//        final ProgressLoading loading = new ProgressLoading(context);
//        loading.showLoading();
//        AndroidNetworking.initialize(context);
//        AndroidNetworking.post(Urls.DELETE_NOTIFICATIONS)
//                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
//                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
//                .addBodyParameter("userId", user_id)
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        loading.cancelLoading();
//                        Log.e("responseAds", "" + response);
//                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
//                        }.getType());
//                        if (serverResponse.getStatus() == 200) {
//                            view.isDeleted(true);
//                        } else {
//                            view.isDeleted(false);
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        loading.cancelLoading();
//                    }
//                });
//    }

    public void setNotificationRead(Context context, String user_id) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.SET_ALL_NOTIFICATION_READ)
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("userId", user_id)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        Log.e("responseAds", "" + response);
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            view.isRead(true);
                        } else {
                            view.isRead(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }


//    public void updateOrder(final Context context, String tripId, String userId, String status,String code) {
//        final ProgressLoading loading = new ProgressLoading(context);
//        loading.showLoading();
//        AndroidNetworking.initialize(context);
//        AndroidNetworking.post(Urls.UPDATE_AGREEMENTS)
//                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
//                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
//                .addBodyParameter("agrId", tripId)
//                .addBodyParameter("userId", userId)
//                .addBodyParameter("status", status)
//                .addBodyParameter("code",code)
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        loading.cancelLoading();
//                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
//                        }.getType());
//                        if (serverResponse.getStatus() == 200) {
//                            loading.cancelLoading();
//                            view.statusUpdate(true);
//                        }
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        Log.e("error", "" + anError.getMessage());
//                    }
//                });
//
//    }
}
