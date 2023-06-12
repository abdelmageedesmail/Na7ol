package com.art4muslim.na7ol.ui.orderFragment;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.art4muslim.na7ol.internet.Urls;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.internet.model.DeliveryMethodModel;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.internet.model.ServerResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;
import com.art4muslim.na7ol.utils.ProgressLoading;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class TripsPresenter {
    TripsView view;
    private boolean canLoadMore;

    public void setView(TripsView view) {
        this.view = view;
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

    public void getOrdersTypes(Context context) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_ORDER_TYPES + Utils.getLang(context))
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
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
                            OrderTypeResponse orderTypeResponse = new Gson().fromJson(response.toString(), new TypeToken<OrderTypeResponse>() {
                            }.getType());

                            view.getOrderTypes(orderTypeResponse.getReturns());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });

    }


    public void getCarTypes(Context context) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GETCARTYPES + Utils.getLang(context))
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
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
                            CarTypeResponse carTypeResponse = new Gson().fromJson(response.toString(), new TypeToken<CarTypeResponse>() {
                            }.getType());

                            view.getCarTypes(carTypeResponse.getReturns());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });

    }

    public void getAds(Context context, String serv_type, String carType, String flightType) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.GET_TRIPS + Utils.getLang(context));
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("serv_type", serv_type)
                .addBodyParameter("carType", carType)
                .addBodyParameter("onlyWaiting", "1");
        if (!flightType.isEmpty()) {
            post.addBodyParameter("servTravelType", flightType);
        }
        post.setPriority(Priority.MEDIUM)
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
//                            if (adsResponse.getTotal_items() != adsResponse.getTotal_pages()) {
//                                canLoadMore = true;
//                            } else {
//                                canLoadMore = false;
//                            }
                            view.getTrips(adsResponse.getReturns(), canLoadMore);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });

    }

    public void filterAds(final Context context, String toCountryId, String fromCityId, String toCityId, String fromCountryId, String carType
            , String from_time, String to_time, String orderTypes, String serv_type, String servWight) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.GET_TRIPS + Utils.getLang(context));
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("serv_type", serv_type);
        if (!carType.isEmpty()) {
            post.addBodyParameter("carType", carType);
        }
        if (!toCountryId.isEmpty()) {
            post.addBodyParameter("toCountryId", toCountryId);
        }
        if (!fromCityId.isEmpty()) {
            post.addBodyParameter("fromCityId", fromCityId);
        }
        if (!toCityId.isEmpty()) {
            post.addBodyParameter("toCityId", toCityId);
        }
        if (!fromCountryId.isEmpty()) {
            post.addBodyParameter("fromCountryId", fromCountryId);
        }
        if (!from_time.isEmpty() && !from_time.equals("null null")) {
            post.addBodyParameter("from_time", from_time);
        }
        if (!to_time.isEmpty() && !to_time.equals("null null")) {
            post.addBodyParameter("to_time", to_time);
        }
        if (!orderTypes.isEmpty()) {
            post.addBodyParameter("orderTypes", orderTypes);
        }
        if (!servWight.isEmpty()) {
            post.addBodyParameter("weightId", servWight);
        }
        post.setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("filterResponse", "" + response.length());
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            TripsModelResponse adsResponse = new Gson().fromJson(response.toString(), new TypeToken<TripsModelResponse>() {
                            }.getType());
                            if (adsResponse.getReturns().size() > 0) {
                                view.getTrips(adsResponse.getReturns(), false);
                            } else {
                                view.getTrips(adsResponse.getReturns(), true);
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

    public void getTripFlight(Context context, String serv_type, String carType, String flightType) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.GET_TRIPS + Utils.getLang(context));
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("serv_type", serv_type)
                .addBodyParameter("carType", carType)
                .addBodyParameter("onlyWaiting", "1");
        if (carType.equals("3")) {
            post.addBodyParameter("servTravelType", flightType);
        }
        post.setPriority(Priority.MEDIUM)
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
//                            if (adsResponse.getTotal_items() != adsResponse.getTotal_pages()) {
//                                canLoadMore = true;
//                            } else {
//                                canLoadMore = false;
//                            }
                            view.getTrips(adsResponse.getReturns(), canLoadMore);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });

    }

    public void getFilterAds(Context context, String serv_type, String carType, String toCountryId, String fromCityId, String toCityId, String fromCountryId, String date) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.GET_TRIPS + Utils.getLang(context));
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("serv_type", serv_type)
                .addBodyParameter("carType", carType)
                .addBodyParameter("toCountryId", toCountryId)
                .addBodyParameter("fromCityId", fromCityId)
                .addBodyParameter("toCityId", toCityId)
                .addBodyParameter("fromCountryId", fromCountryId);
        if (!date.isEmpty()) {
            post.addBodyParameter("date", date);
        }

        post.setPriority(Priority.MEDIUM)
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
//                            if (adsResponse.getTotal_items() != adsResponse.getTotal_pages()) {
//                                canLoadMore = true;
//                            } else {
//                                canLoadMore = false;
//                            }
                            view.getFliterTrips(adsResponse.getReturns(), canLoadMore);
                        } else {
                            view.getFliterTrips(null, canLoadMore);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });

    }

    public void joinTrip(final Context context, String toId, String fromId, String servId, String weight, String orderType, String packageDetails, String receiverName,
                         String receiverMobile, String deliveryType, File imageData, String cost) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.upload(Urls.ADD_AGREEMENT)
                .addMultipartParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addMultipartParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addMultipartParameter("toId", toId)
                .addMultipartParameter("fromId", fromId)
                .addMultipartParameter("servId", servId)
                .addMultipartParameter("orderType", orderType)
                .addMultipartParameter("cost", cost)
                .addMultipartParameter("type", "order")
                .addMultipartParameter("packageDetails", packageDetails)
                .addMultipartParameter("receiverName", receiverName)
                .addMultipartParameter("receiverMobile", receiverMobile)
                .addMultipartParameter("deliveryType", deliveryType)
                .addMultipartParameter("weight", weight)
                .addMultipartFile("imageData", imageData)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            view.isJoined(true);
                        } else {
                            try {
                                String message = response.getString("message");
                                Toast.makeText(context, "" + message, Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            view.isJoined(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }

    public void getDeliveryMethod(Context context) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_DELIVERY_METHOD + Utils.getLang(context))
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
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
                            DeliveryMethodModel deliveryMethodModel = new Gson().fromJson(response.toString(), new TypeToken<DeliveryMethodModel>() {
                            }.getType());
                            view.getMethods(deliveryMethodModel.getReturns());
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
