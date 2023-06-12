package com.art4muslim.na7ol.ui.chooseTripType;

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
import com.art4muslim.na7ol.internet.model.ServerResponse;
import com.art4muslim.na7ol.internet.model.UserResponse;
import com.art4muslim.na7ol.utils.ProgressLoading;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class UserPresenter {

    UserView view;

    public void setView(UserView view) {
        this.view = view;
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

}
