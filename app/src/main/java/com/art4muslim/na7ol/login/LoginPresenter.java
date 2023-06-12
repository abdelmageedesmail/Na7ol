package com.art4muslim.na7ol.login;

import android.content.Context;
import android.content.Intent;
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
import com.art4muslim.na7ol.ui.forgetPassword.EnterCodeActivity;
import com.art4muslim.na7ol.utils.ProgressLoading;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter {

    private LoginView view;

    public void setView(LoginView view) {
        this.view = view;
    }


    public void loginUser(final Context context, String user_email, String user_pass) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);

        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Urls.LOGIN);
        post.addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("mobile", user_email)
                .addBodyParameter("password", user_pass)
                .addQueryParameter("api_lang", Utils.getLang(context));
        post.setPriority(Priority.MEDIUM)
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
//                            Toast.makeText(context, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                String message = response.getString("message");
                                String sub_message = response.getString("sub_message");
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                                if (sub_message.equals("inactive")) {
                                    Intent intent = new Intent(context, EnterCodeActivity.class);
                                    intent.putExtra("mobile", user_email);
                                    intent.putExtra("from", "register");
                                    context.startActivity(intent);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            view.getUserData(null);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.cancelLoading();
                    }
                });
    }
}
