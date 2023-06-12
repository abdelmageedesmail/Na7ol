package com.art4muslim.na7ol.ui.profile;

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
import com.art4muslim.na7ol.utils.ProgressLoading;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class ProfilePresenter {

    EditDataView view;

    public void setView(EditDataView view) {
        this.view = view;
    }

    public void editData(final Context context, String userId, String uname, String mobile, String email, File imageData) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        ANRequest.MultiPartBuilder upload = AndroidNetworking.upload(Urls.EDIT_DATA);
        upload.addMultipartParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addMultipartParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addMultipartParameter("userId", userId)
                .addMultipartParameter("uname", uname)
                .addMultipartParameter("mobile", mobile)
                .addMultipartParameter("email", email);
        if (imageData != null) {
            upload.addMultipartFile("imageData", imageData);
        }
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
                            view.getUserData(loginResponse.getReturns());
                            view.isUpdated(true);
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

    public void editPass(final Context context, String userId, String password) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.upload(Urls.EDIT_DATA)
                .addMultipartParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addMultipartParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addMultipartParameter("userId", userId)
                .addMultipartParameter("password", password)
                .setPriority(Priority.MEDIUM)
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
                            view.isUpdated(true);
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

    public void editImage(final Context context, String userId, File imageData) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.upload(Urls.EDIT_DATA)
                .addMultipartParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addMultipartParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addMultipartFile("imageData", imageData)
                .addMultipartParameter("userId", userId)
                .setPriority(Priority.MEDIUM)
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
                            view.getUserData(loginResponse.getReturns());
                            view.isUpdated(true);
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
                        Log.e("error", "" + anError.getErrorBody());
                    }
                });
    }
}
