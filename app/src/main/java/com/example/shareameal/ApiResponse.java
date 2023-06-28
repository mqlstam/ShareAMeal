package com.example.shareameal;

import java.util.List;

public class ApiResponse {
    private List<Meal> result;

    public ApiResponse(List<Meal> result) {
        this.result = result;
    }

    public List<Meal> getResult() {

        return result;
    }

}
