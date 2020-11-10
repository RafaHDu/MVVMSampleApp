package com.example.mvvmsampleapp.util;

import android.util.Log;

import com.example.mvvmsampleapp.models.Recipe;

import java.util.List;

public class Testing {

    public static void printRecipes(List<Recipe> recipes, String TAG){
        for(Recipe recipe : recipes){
            Log.d(TAG, "onChanged: " + recipe.getTitle());
        }
    }

}
