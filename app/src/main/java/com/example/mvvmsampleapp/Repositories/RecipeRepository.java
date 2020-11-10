package com.example.mvvmsampleapp.Repositories;

import androidx.lifecycle.LiveData;

import com.example.mvvmsampleapp.models.Recipe;
import com.example.mvvmsampleapp.requests.RecipeApiClient;

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

    private RecipeApiClient recipeApiClient;

    private RecipeRepository() {
        recipeApiClient = RecipeApiClient.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return recipeApiClient.getRecipes();
    }

    public void searchRecipeApi(String query, int pageNumber){
        if(pageNumber == 0){
            pageNumber = 1;
        }
        recipeApiClient.searchRecipeApi(query, pageNumber);
    }

}
