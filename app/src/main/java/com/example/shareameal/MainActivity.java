package com.example.shareameal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MealAdapter mealAdapter;
    private MealViewModel viewModel;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        int numberOfColumns = getResources().getInteger(R.integer.grid_columns);
        GridLayoutManager layoutManager = new GridLayoutManager(this, numberOfColumns);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        viewModel = new ViewModelProvider(this).get(MealViewModel.class);

        loadData();
    }

    private void loadData() {
        if (isNetworkConnected()) {
            Log.d(TAG, "loadData: Connected to the internet, fetching meals from API");
            getMealsFromApi();
        } else {
            Log.d(TAG, "loadData: No internet connection, loading meals from local database");
            getMealsFromDatabase();
            Toast.makeText(MainActivity.this, "No internet connection. Using local database.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private void getMealsFromApi() {
        ApiClient.getInstance().getApi().getMeals().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse apiResponse = response.body();
                    List<Meal> meals = apiResponse.getResult();
                    Log.d(TAG, "getMealsFromApi: onResponse: successfully fetched " + meals.size() + " meals from API");
                    viewModel.insertAll(meals);
                    getMealsFromDatabase();
                    Toast.makeText(MainActivity.this, getString(R.string.items_loaded, meals.size()), Toast.LENGTH_LONG).show();
                } else {
                    Log.e(TAG, "getMealsFromApi: onResponse: failed to fetch meals from API, response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e(TAG, "getMealsFromApi: onFailure: error fetching meals from API: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMealsFromDatabase() {
        viewModel.getAllMeals().observe(this, meals -> {
            Log.d(TAG, "getMealsFromDatabase: received " + meals.size() + " meals from local database");
            mealAdapter = new MealAdapter(meals, MainActivity.this);
            recyclerView.setAdapter(mealAdapter);
        });
    }
}