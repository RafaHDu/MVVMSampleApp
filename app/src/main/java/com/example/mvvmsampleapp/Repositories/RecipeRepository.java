package com.example.mvvmsampleapp.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmsampleapp.models.Recipe;

import java.util.List;

public class RecipeRepository {

    //Singleton Pattern
    private static RecipeRepository instance;
    public static RecipeRepository getInstance(){
        if(instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }

    private MutableLiveData<List<Recipe>> mRecipes;

    private RecipeRepository() {
        mRecipes = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }
}
