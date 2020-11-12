package com.example.mvvmsampleapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmsampleapp.Repositories.RecipeRepository;
import com.example.mvvmsampleapp.models.Recipe;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    //This VieModel will be responsible to getting/holding/retrieving the recipes that will be displayed in the application

    private RecipeRepository recipeRepository;
    private boolean isViewingRecipes;

    public RecipeListViewModel() {
        isViewingRecipes = false;
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return recipeRepository.getRecipes();
    }

    public void searchRecipeApi(String query, int pageNumber){
        isViewingRecipes = true;
        recipeRepository.searchRecipeApi(query, pageNumber);
    }

    public boolean isViewingRecipes() {
        return isViewingRecipes;
    }

    public void setViewingRecipes(boolean viewingRecipes) {
        isViewingRecipes = viewingRecipes;
    }
}
