package com.art4muslim.na7ol.ui.myCharge;

import android.content.Context;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.art4muslim.na7ol.internet.Urls;
import com.art4muslim.na7ol.internet.model.CitiesResponse;
import com.art4muslim.na7ol.internet.model.CountriesResponse;
import com.art4muslim.na7ol.internet.model.ServerResponse;
import com.art4muslim.na7ol.ui.joinTransporter.JoinTransporterView;
import com.art4muslim.na7ol.utils.ProgressLoading;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class ChargePresenter {
    ChargeView view;
    private ANRequest.MultiPartBuilder post;

    public void setView(ChargeView tripView) {
        this.view = tripView;
    }

    public void getBalance(Context context, String userId) {
        Log.e("langg", "" + Utils.getLang(context));
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.GET_BALANCE)
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("userId", userId)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        loading.cancelLoading();
                        ServerResponse serverResponse = new Gson().fromJson(response.toString(), new TypeToken<ServerResponse>() {
                        }.getType());
                        if (serverResponse.getStatus() == 200) {
                            try {
                                String total_balance = response.getString("total_balance");
                                String total_app_commision = response.getString("total_app_commision");
                                String remailn_balance = response.getString("remailn_balance");
                                view.getBalance(total_balance, total_app_commision, remailn_balance);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getBalance("", "", "");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }
}
