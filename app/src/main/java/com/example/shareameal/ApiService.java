package com.example.shareameal;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("api/meal")
    Call<ApiResponse> getMeals();
}

