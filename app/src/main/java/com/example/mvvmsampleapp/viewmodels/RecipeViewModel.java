package com.example.mvvmsampleapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmsampleapp.Repositories.RecipeRepository;
import com.example.mvvmsampleapp.models.Recipe;

import java.util.List;

public class RecipeViewModel extends ViewModel {

    //This VieModel will be responsible to getting/holding/retrieving the recipes that will be displayed in the application

    private RecipeRepository recipeRepository;
    private String recipeId;

    public RecipeViewModel() {
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<Recipe> getRecipe(){
        return recipeRepository.getRecipe();
    }

    public void searchRecipeById(String rId){
        recipeId = rId;
        recipeRepository.searchRecipeById(rId);
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

}