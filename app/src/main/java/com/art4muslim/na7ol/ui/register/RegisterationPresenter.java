package com.art4muslim.na7ol.ui.register;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.art4muslim.na7ol.internet.Urls;
import com.art4muslim.na7ol.internet.model.ServerResponse;
import com.art4muslim.na7ol.internet.model.UserResponse;
import com.art4muslim.na7ol.login.LoginActivity;
import com.art4muslim.na7ol.ui.forgetPassword.EnterCodeActivity;
import com.art4muslim.na7ol.utils.ProgressLoading;
import com.art4muslim.na7ol.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterationPresenter {

    private RegisterationView view;

    public void setView(RegisterationView view) {
        this.view = view;
    }


    public void registerUser(final Context context, String uname, String mobile, String email, String password) {
        final ProgressLoading loading = new ProgressLoading(context);
        loading.showLoading();
        AndroidNetworking.initialize(context);
        Log.e("apiLang", Utils.getLang(context));
        AndroidNetworking.post(Urls.REGISTER)
                .addBodyParameter("access_key", "Wqka52DASWE3DSasWE742Wq")
                .addBodyParameter("access_password", "5e52dDDF85gffEWqPNV7D12sW5e")
                .addBodyParameter("uname", uname)
                .addBodyParameter("mobile", mobile)
                .addBodyParameter("email", email)
                .addBodyParameter("userType", "user")
                .addBodyParameter("password", password)
                .addQueryParameter("api_lang", Utils.getLang(context))
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
                            view.getUserDetails(loginResponse.getReturns());
                            Toast.makeText(context, "" + loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                JSONArray user_data = response.getJSONArray("user_data");
                                JSONObject jsonObject = user_data.getJSONObject(0);

                                if (jsonObject.getString("adv_status").equals("1")) {
                                    Toast.makeText(context, "loginnn", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    context.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(context, EnterCodeActivity.class);
                                    intent.putExtra("mobile", mobile);
                                    intent.putExtra("from", "register");
                                    context.startActivity(intent);
                                }
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
}
