package com.art4muslim.na7ol.ui.add_trip;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.art4muslim.na7ol.internet.Urls;
import com.art4muslim.na7ol.internet.model.CarTypeResponse;
import com.art4muslim.na7ol.internet.model.CitiesResponse;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.internet.model.OrderTypeResponse;
import com.art4muslim.na7ol.internet.model.ServerResponse;
import com.art4muslim.na7ol.internet.model.WeightsResponse;
import com.art4muslim.na7ol.ui.orderFragment.TripsModelResponse;
import com.art4muslim.na7ol.utils.ProgressLoading;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class TripPresenter {
    TripView view;
    private ANRequest.MultiPartBuilder post;

    public void setTripView(TripView tripView) {
        this.view = tripView;
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

    public void getCities(Context context, String countryID) {
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

    public void addTrip(final Context context, String driverId, String carTypeId, String fromCountry,
                        String fromCity, String toCountry, String toCity, String servType, String servTravelType, String toTime,
                        String fromTime, String servDetails, String servWeight, String serv_order_type_id, String serv_receiver_name, String serv_receiver_mobile, String serv_time, File imageData) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        post = AndroidNetworking.upload(Urls.ADD_TRIP);
        post.addMultipartParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addMultipartParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")

                .addMultipartParameter("carTypeId", carTypeId)
                .addMultipartParameter("fromCountry", fromCountry)
                .addMultipartParameter("fromCity", fromCity)
                .addMultipartParameter("toCountry", toCountry)
                .addMultipartParameter("serv_type", servType)
                .addMultipartParameter("toCity", toCity)
                .addMultipartParameter("servDetails", servDetails)
                .addMultipartParameter("servWeight", servWeight);
        if (!fromTime.isEmpty()) {
            post.addMultipartParameter("toTime", toTime)
                    .addMultipartParameter("fromTime", fromTime);
        }
        if (carTypeId != null) {
            if (carTypeId.equals("3")) {

                post.addMultipartParameter("servTravelType", servTravelType);
            }
        }

        if (servType.equals("order")) {
            post.addMultipartParameter("serv_order_type_id", serv_order_type_id)
                    .addMultipartParameter("userId", driverId)
                    .addMultipartParameter("serv_receiver_name", serv_receiver_name)
                    .addMultipartParameter("serv_time", serv_time)
                    .addMultipartFile("imageData", imageData)
                    .addMultipartParameter("toTime", "2020-10-10 00:00:00")
                    .addMultipartParameter("fromTime", "2020-10-10 00:00:00")
                    .addMultipartParameter("serv_receiver_mobile", serv_receiver_mobile);
        } else {
            post.addMultipartParameter("driverId", driverId);
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
                            view.addTrip(true);
                        } else {
                            view.addTrip(false);
                            try {
                                String message = response.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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

    public void editTrip(final Context context, String driverId, String carTypeId, String fromCountry,
                         String fromCity, String toCountry, String toCity, String servType, String servTravelType, String toTime,
                         String fromTime, String servDetails, String servWeight, String serv_order_type_id, String serv_receiver_name, String serv_receiver_mobile, String serv_time, File imageData, String tripId) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        post = AndroidNetworking.upload(Urls.UPDATE_TRIP);
        post.addMultipartParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addMultipartParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addMultipartParameter("tripId", tripId)
                .addMultipartParameter("carTypeId", carTypeId)
                .addMultipartParameter("fromCountry", fromCountry)
                .addMultipartParameter("fromCity", fromCity)
                .addMultipartParameter("toCountry", toCountry)
                .addMultipartParameter("serv_type", servType)
                .addMultipartParameter("toCity", toCity)
                .addMultipartParameter("servDetails", servDetails)
                .addMultipartParameter("servWeight", servWeight);
        if (!fromTime.isEmpty()) {
            post.addMultipartParameter("toTime", toTime)
                    .addMultipartParameter("fromTime", fromTime);
        }
        if (carTypeId != null) {
            if (carTypeId.equals("3")) {

                post.addMultipartParameter("servTravelType", servTravelType);
            }
        }

        if (servType.equals("order")) {
            post.addMultipartParameter("serv_order_type_id", serv_order_type_id)
                    .addMultipartParameter("userId", driverId)
                    .addMultipartParameter("serv_receiver_name", serv_receiver_name)
                    .addMultipartParameter("serv_time", serv_time)
                    .addMultipartParameter("toTime", "2020-10-10 00:00:00")
                    .addMultipartParameter("fromTime", "2020-10-10 00:00:00")
                    .addMultipartParameter("serv_receiver_mobile", serv_receiver_mobile);
            if (imageData != null) {
                post.addMultipartFile("imageData", imageData);

            }
        } else {
            post.addMultipartParameter("driverId", driverId);
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
                            view.addTrip(true);
                        } else {
                            view.addTrip(false);
                            try {
                                String message = response.getString("message");
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
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


    public void getAds(final Context context, String tripId) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_TRIP + Utils.getLang(context))
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("tripId", tripId)
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
}
