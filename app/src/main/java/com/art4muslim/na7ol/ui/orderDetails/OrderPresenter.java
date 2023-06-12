package com.art4muslim.na7ol.ui.orderDetails;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.art4muslim.na7ol.internet.Urls;
import com.art4muslim.na7ol.internet.model.ServerResponse;
import com.art4muslim.na7ol.internet.model.UserResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;
import com.art4muslim.na7ol.utils.ProgressLoading;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderPresenter {
    OrderView view;

    public void setView(OrderView view) {
        this.view = view;
    }

    public void joinTrip(Context context, String toId, String servId, String weight) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.ADD_AGREEMENT)
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("toId", toId)
                .addBodyParameter("fromId", toId)
                .addBodyParameter("servId", servId)
                .addBodyParameter("cost", "100")
                .addBodyParameter("type", "order")
                .addBodyParameter("packageDetails", "packageDetails")
                .addBodyParameter("weight", weight)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            view.isAdded(true);
                        } else {
                            view.isAdded(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }


    public void getWeights(Context context) {
        Log.e("langg", "" + Utils.getLang(context));
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_WEIGHTS + Utils.getLang(context))
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            WeightsResponse weightsResponse = new Gson().fromJson(response.toString(), new TypeToken<WeightsResponse>() {
                            }.getType());
                            view.getWeights(weightsResponse.getReturns());
                        } else {
                            view.getWeights(null);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }


    public void getUserData(final Context context, String userId) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.MultiPartBuilder upload = AndroidNetworking.upload(Urls.GET_USER_DATA);
        upload.addMultipartParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addMultipartParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addMultipartParameter("userId", userId);
        upload.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            UserResponse loginResponse = new Gson().fromJson(response.toString(), new TypeToken<UserResponse>() {
                            }.getType());
                            view.isTransporter(loginResponse.getReturns().getAdv_driver_status());
                        } else {
                            try {
                                Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }

    public void addOffer(final Context context, String fromId, String toId, String servId, String weight, String cost) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.ADD_AGREEMENT)
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("toId", toId)
                .addBodyParameter("fromId", fromId)
                .addBodyParameter("servId", servId)
                .addBodyParameter("cost", cost)
                .addBodyParameter("type", "price")
                .addBodyParameter("packageDetails", "packageDetails")
                .addBodyParameter("weight", weight)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            view.isAdded(true);
                        } else {
                            try {
                                String message = response.getString("message");
                                Toast.makeText(context,""+ message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }

}
