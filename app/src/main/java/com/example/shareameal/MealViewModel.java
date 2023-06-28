package com.example.shareameal;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MealViewModel extends AndroidViewModel {
    private MealRepository repository;
    private LiveData<List<Meal>> allMeals;

    public MealViewModel(Application application) {
        super(application);
        repository = new MealRepository(application);
        allMeals = repository.getAllMeals();
    }

    LiveData<List<Meal>> getAllMeals() {
        return allMeals;
    }

    public void insertAll(List<Meal> meals) {
        repository.insertAll(meals);
    }
}