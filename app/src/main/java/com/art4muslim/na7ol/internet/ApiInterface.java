package com.art4muslim.na7ol.internet;

import com.art4muslim.na7ol.internet.model.UserResponse;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("signup")
    public Call<UserResponse> signUp(String access_key,
                                     String access_password,
                                     String uname,
                                     String mobile,
                                     String email,
                                     String password);


}
