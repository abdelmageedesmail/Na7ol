package com.art4muslim.na7ol.ui.notificationSettings;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.art4muslim.na7ol.R;
import com.art4muslim.na7ol.internet.Urls;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.internet.model.CitiesResponse;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.internet.model.NotificationSettingResponse;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.internet.model.ServerResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;
import com.art4muslim.na7ol.ui.add_trip.TripView;
import com.art4muslim.na7ol.utils.ProgressLoading;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

public class NotificationPresenter {

    public NotificationView view;

    public void setView(NotificationView view) {
        this.view = view;
    }


    public void getCountries(Context context) {
        Log.e("langg", "" + Utils.getLang(context));
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_COUNTRISE + Utils.getLang(context))
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
                            CountriesResponse countriesResponse = new Gson().fromJson(response.toString(), new TypeToken<CountriesResponse>() {
                            }.getType());
                            view.getCountries(countriesResponse.getReturns());
                        } else {
                            view.getCountries(null);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
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
                            view.getCountries(null);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }

    public void getCities(final Context context, String countryID) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_CITIES + Utils.getLang(context))
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("countryId", countryID)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            CitiesResponse citiesResponse = new Gson().fromJson(response.toString(), new TypeToken<CitiesResponse>() {
                            }.getType());
                            CitiesResponse.Return cities = new CitiesResponse.Return();
                            cities.setCity_id(0);
                            cities.setCity_name(context.getString(R.string.all));
                            citiesResponse.getReturns().add(0, cities);
                            view.getCities(citiesResponse.getReturns());
                        } else {
                            view.getCities(null);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }


    public void updateNotifications(Context context, String userId, String adv_not_trips, String adv_not_orders, String adv_not_car_type, String adv_not_city_from, String adv_not_city_to, String adv_not_order_type, String adv_not_weight) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.UPDATE_NOTIFICATIONS);
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("userId", userId)
                .addBodyParameter("adv_not_trips", adv_not_trips)
                .addBodyParameter("adv_not_orders", adv_not_orders);
        if (adv_not_car_type != null) {
            post.addBodyParameter("adv_not_car_type", adv_not_car_type);
        } else {
            post.addBodyParameter("adv_not_car_type", "0");
        }
        if (adv_not_city_from != null) {
            post.addBodyParameter("adv_not_city_from", adv_not_city_from);
        } else {
            post.addBodyParameter("adv_not_city_from", "0");
        }
        if (adv_not_city_to != null) {
            post.addBodyParameter("adv_not_city_to", adv_not_city_to);
        } else {
            post.addBodyParameter("adv_not_city_to", "0");
        }
        if (adv_not_order_type != null) {
            post.addBodyParameter("adv_not_order_type", adv_not_order_type);
        } else {
            post.addBodyParameter("adv_not_order_type", "0");
        }
        if (adv_not_weight != null) {
            post.addBodyParameter("adv_not_weight", adv_not_weight);
        } else {
            post.addBodyParameter("adv_not_weight", "0");
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
                            view.updateNotification(true);
                        } else {
                            view.updateNotification(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                        Log.e("error", "" + anError.getMessage());
                    }
                });
    }

    public void getNotificationSettings(Context context, String userId) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_NOTIFICATIOSSETTING)
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
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
                            NotificationSettingResponse notificationSettingResponse = new Gson().fromJson(response.toString(), new TypeToken<NotificationSettingResponse>() {
                            }.getType());
                            view.getNotificationSettings(notificationSettingResponse.getReturns());
                        } else {
                            view.updateNotification(false);
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
