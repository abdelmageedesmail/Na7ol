package com.art4muslim.na7ol.ui.register;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.art4muslim.na7ol.internet.ApiClient;
import com.art4muslim.na7ol.internet.model.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {

    public MutableLiveData<UserResponse> mutableLiveData = new MutableLiveData<>();

    public void signUp(String name, String phone, String email, String password) {
        ApiClient.getINSTACNC().signUp(name, phone, email, password).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    mutableLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }
}
