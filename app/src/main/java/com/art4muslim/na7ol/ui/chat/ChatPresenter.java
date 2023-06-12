package com.art4muslim.na7ol.ui.chat;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.art4muslim.na7ol.internet.Urls;
import com.art4muslim.na7ol.internet.model.MessagesResponse;
import com.art4muslim.na7ol.internet.model.ServerResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.File;

public class ChatPresenter {
    ChatView view;
    private boolean canLoadMore;

    public void setView(ChatView view) {
        this.view = view;
    }

    public void sendMessage(final Context context, String from, String to, String message, File imageData, String itemId) {
        AndroidNetworking.initialize(context);
        ANRequest.MultiPartBuilder upload = AndroidNetworking.upload(Urls.SEND_MESSAGE);
        upload.addMultipartParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addMultipartParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addMultipartParameter("fromUserId", from)
                .addMultipartParameter("toUserId", to)
                .addMultipartParameter("itemId", itemId);
        if (!message.isEmpty()) {
            upload.addMultipartParameter("message", message);
        }
        if (imageData != null) {
            upload.addMultipartFile("imageData", imageData);
        }
        upload.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("responseMessage", "" + response);
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            view.messageSent(true);
                        } else {
                            view.messageSent(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }

    public void addChatNotifications(final Context context, String senderId, String receiverId, String message, String serv_id) {
        AndroidNetworking.initialize(context);
        ANRequest.MultiPartBuilder upload = AndroidNetworking.upload(Urls.ADD_CHAT_NOTIFICATIONS);
        upload.addMultipartParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addMultipartParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addMultipartParameter("senderId", senderId)
                .addMultipartParameter("receiverId", receiverId)
                .addMultipartParameter("serv_id", serv_id)
                .addMultipartParameter("message", message);
        upload.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("responseMessage", "" + response);
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
//                            view.messageSent(true);
                        } else {
//                            view.messageSent(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", "" + anError.getMessage());
                    }
                });

    }

    public void getMessages(Context context, String userId, String otherUserId) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_MESSAGES)
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("userId", userId)
                .addBodyParameter("otherUserId", otherUserId)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            MessagesResponse chatModelResponse = new Gson().fromJson(response.toString(), new TypeToken<MessagesResponse>() {
                            }.getType());
                            Log.e("responsesss", "" + chatModelResponse.getReturns().size());
                            view.getChats(chatModelResponse.getReturns());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("error", "" + anError.getMessage());
                    }
                });

    }

//    public void updateOrder(final Context context, String servId, String fromId, String toId, String cost) {
//        final ProgressLoading loading = new ProgressLoading(context);
//        loading.showLoading();
//        AndroidNetworking.initialize(context);
//        AndroidNetworking.post(Urls.ADD_ARGUMENT)
//                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
//                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
//                .addBodyParameter("servId", servId)
//                .addBodyParameter("fromId", fromId)
//                .addBodyParameter("toId", toId)
//                .addBodyParameter("cost", cost)
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

//    public void getTripDetails(final Context context, String tripId) {
//        final ProgressLoading loading = new ProgressLoading(context);
//        loading.showLoading();
//        AndroidNetworking.initialize(context);
//        AndroidNetworking.post(Urls.GET_TRIP)
//                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
//                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
//                .addBodyParameter("tripId", tripId)
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        loading.cancelLoading();
//                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
//                        }.getType());
//                        if (serverResponse.getStatus() == 200) {
//                            TripsModelResponse adsResponse = new Gson().fromJson(response.toString(), new TypeToken<TripsModelResponse>() {
//                            }.getType());
////                            if (adsResponse.getTotal_items() != adsResponse.getTotal_pages()) {
////                                canLoadMore = true;
////                            } else {
////                                canLoadMore = false;
////                            }
//                            view.getTrips(adsResponse.getReturns());
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
