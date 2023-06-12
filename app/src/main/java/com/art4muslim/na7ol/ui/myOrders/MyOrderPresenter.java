package com.art4muslim.na7ol.ui.myOrders;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.art4muslim.na7ol.internet.Urls;
import com.art4muslim.na7ol.internet.model.AgreementsResponseModel;
import com.art4muslim.na7ol.internet.model.RateResponse;
import com.art4muslim.na7ol.internet.model.ServerResponse;
import com.art4muslim.na7ol.internet.model.TermsResponseModel;
import com.art4muslim.na7ol.ui.myTrips.MyTrips;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.utils.ProgressLoading;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

public class MyOrderPresenter {

    MyOrderView view;

    public void setView(MyOrderView view) {
        this.view = view;
    }


//                    .addBodyParameter("getBoth", "true")
    public void getAgreements(Context context, String status, String userId) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.GET_AGREEMENTS + Utils.getLang(context));
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("status", status)
                .addBodyParameter("userId", userId);
        post.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            AgreementsResponseModel agreementsResponseModel = new Gson().fromJson(response.toString(), new TypeToken<AgreementsResponseModel>() {
                            }.getType());
                            view.getAgreements(agreementsResponseModel.getReturns());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }

    public void getTerms(Context context) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.GET_TERMS + Utils.getLang(context));
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            TermsResponseModel agreementsResponseModel = new Gson().fromJson(response.toString(), new TypeToken<TermsResponseModel>() {
                            }.getType());
                            view.getTerms(agreementsResponseModel.getReturns());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }

    public void writeContract(final Context context, final String tripId, String driverId, String contract) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.ADD_TRIP_CONTRACT);
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("tripId", tripId)
                .addBodyParameter("driverId", driverId)
                .addBodyParameter("contractText", contract)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            view.isSent(true);
                        } else {
                            view.isSent(false);
                            try {
                                String message = response.getString("message");
                                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
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

    public void getTripsAgreements(Context context, String status, String userId) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.GET_AGREEMENTS + Utils.getLang(context));
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("status", status)
                .addBodyParameter("ownerId", userId)
                .addBodyParameter("getBoth", "true");
        post.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            AgreementsResponseModel agreementsResponseModel = new Gson().fromJson(response.toString(), new TypeToken<AgreementsResponseModel>() {
                            }.getType());
                            view.getAgreements(agreementsResponseModel.getReturns());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }


    public void getAds(Context context, String serv_type, String userId, String status) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_TRIPS + Utils.getLang(context))
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("serv_type", serv_type)
                .addBodyParameter("status", status)
                .addBodyParameter("userId", userId)
                .addBodyParameter("onlyWaiting", "1")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            TripsModelResponse adsResponse = new Gson().fromJson(response.toString(), new TypeToken<TripsModelResponse>() {
                            }.getType());
                            view.getTrips(adsResponse.getReturns());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });

    }

    public void updateTripStatus(Context context, String tripId, String userId, String status) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.UPDATE_TRIP_STATUS)
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("tripId", tripId)
                .addBodyParameter("status", status)
                .addBodyParameter("userId", userId)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            view.isCanceled(true);
                        } else {
                            view.isCanceled(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });

    }

    public void updateTripCost(Context context, String tripId, String userId, final String status, String cost) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.UPDATE_TRIP_STATUS)
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("tripId", tripId)
                .addBodyParameter("status", status)
                .addBodyParameter("userId", userId)
                .addBodyParameter("cost", cost)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            view.isUpdated(true, status);
                        } else {
                            view.isUpdated(false, status);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });

    }

    public void updateTripAgreementCost(final Context context, String agrId, String tripId, String userId, String driverId, String cost) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.updateTripAgreement)
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("tripId", tripId)
                .addBodyParameter("agrId", agrId)
                .addBodyParameter("toId", userId)
                .addBodyParameter("fromId", driverId)
                .addBodyParameter("cost", cost)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            view.isUpdated(true, "tripUpdated");
                        } else {
//                            view.isUpdated(false, "tripUpdated");
                            try {
                                String message = response.getString("message");
                                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
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

    public void updateAgreementStatus(Context context, String userId, String agrId, final String status) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.UPDATE_AGREEMENT);
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("userId", userId)
                .addBodyParameter("status", status)
                .addBodyParameter("agrId", agrId);
        post.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            view.isUpdated(true, status);
                        } else {
                            view.isUpdated(false, status);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }

    public void finishTrip(final Context context, String userId, String agrId, final String status, String code) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.UPDATE_AGREEMENT);
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("userId", userId)
                .addBodyParameter("status", status)
                .addBodyParameter("code", code)
                .addBodyParameter("agrId", agrId);
        post.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            view.isUpdated(true, status);
                        } else {
                            try {
                                String message = response.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            view.isUpdated(false, status);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }

    public void getUserRate(Context context, String userId) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.GET_USER_RATE);
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("userId", userId);
        post.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            RateResponse rateResponse = new Gson().fromJson(response.toString(), new TypeToken<RateResponse>() {
                            }.getType());
                            view.getUserRate(rateResponse.getReturns());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }

    public void addRate(Context context, String userId, String advertiserId, String rate, String rateReview, String agrId) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.RATE_USER);
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("userId", userId)
                .addBodyParameter("advertiserId", advertiserId)
                .addBodyParameter("rate", rate)
                .addBodyParameter("rateReview", rateReview)
                .addBodyParameter("agreeId", agrId);
        post.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            view.isRated(true);
                        } else {
                            view.isRated(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }

    public void sendCode(Context context, String agrId) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.SEND_ORDER_CODE);
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("agrId", agrId);
        post.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            view.codeSent(true);
                        } else {
                            view.codeSent(false);

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
