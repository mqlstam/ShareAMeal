package com.example.shareameal;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://shareameal-api.herokuapp.com/";
    private static ApiClient apiClient;
    private static Retrofit retrofit;

    private static final String TAG = "ApiClient";

    private ApiClient() {
        Log.d(TAG, "ApiClient: initializing Retrofit instance");
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiClient getInstance() {
        if (apiClient == null) {
            Log.d(TAG, "getInstance: creating new ApiClient instance");
            apiClient = new ApiClient();
        } else {
            Log.d(TAG, "getInstance: returning existing ApiClient instance");
        }
        return apiClient;
    }

    public ApiService getApi() {
        Log.d(TAG, "getApi: creating ApiService instance");
        return retrofit.create(ApiService.class);
    }
}