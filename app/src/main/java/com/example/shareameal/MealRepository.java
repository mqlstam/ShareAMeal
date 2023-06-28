package com.example.shareameal;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MealRepository {
    private MealDao mealDao;
    private LiveData<List<Meal>> allMeals;
    private final Executor executor;

    public MealRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mealDao = db.mealDao();
        allMeals = mealDao.getAllMeals();
        executor = Executors.newSingleThreadExecutor();
    }

    LiveData<List<Meal>> getAllMeals() {
        return allMeals;
    }

    public void insertAll(List<Meal> meals) {
        executor.execute(() -> mealDao.insertAll(meals));
    }
}