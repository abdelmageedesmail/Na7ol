package com.art4muslim.na7ol.ui.technicalSupport;

import android.content.Context;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import com.art4muslim.na7ol.internet.Urls;
import com.art4muslim.na7ol.internet.model.ServerResponse;
import com.art4muslim.na7ol.utils.ProgressLoading;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

public class ContactUsPresenter {

    ContactUs_view view;

    public void setView(ContactUs_view view) {
        this.view = view;
    }

    public void contactUs(Context context, String msg_name, String msg_email, String msg_details) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.CONTACT_US)
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("name", msg_name)
                .addBodyParameter("email", msg_email)
                .addBodyParameter("message", msg_details)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus()==200) {
                            view.isMSGSend(true);
                        } else {
                            view.isMSGSend(false);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });

    }

}
