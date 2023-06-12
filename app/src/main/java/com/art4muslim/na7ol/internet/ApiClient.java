package com.art4muslim.na7ol.internet;

import com.art4muslim.na7ol.internet.model.UserResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "https://nhol.art4muslim.net/app_services/";
    public ApiInterface apiInterface;
    private static ApiClient INSTACNC;

    public ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiClient getINSTACNC() {
        if (INSTACNC == null) {
            INSTACNC = new ApiClient();
        }
        return INSTACNC;
    }

    public Call<UserResponse> signUp(String name, String phone, String email, String password) {
        return apiInterface.signUp("Wqka52DASWE3DSasWE742Wq", "5e52dDDF85gffEWqPNV7D12sW5e", name, phone, email, password);
    }
}
