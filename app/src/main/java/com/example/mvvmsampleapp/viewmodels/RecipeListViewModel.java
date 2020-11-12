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
    private boolean isPerformingRequest;

    public RecipeListViewModel() {
        isPerformingRequest = false;
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return recipeRepository.getRecipes();
    }

    public void searchRecipeApi(String query, int pageNumber){
        isViewingRecipes = true;
        setPerformingRequest(true);
        recipeRepository.searchRecipeApi(query, pageNumber);
    }

    public boolean isBackPressed(){
        if(isPerformingRequest){
            recipeRepository.cancelRequest();
            isPerformingRequest = false;
        }
        if(isViewingRecipes){
            isViewingRecipes = false;
            return false;
        }
        return true;
    }

    //Getters & Setters
    public boolean isViewingRecipes() {
        return isViewingRecipes;
    }

    public void setViewingRecipes(boolean viewingRecipes) {
        isViewingRecipes = viewingRecipes;
    }

    public boolean isPerformingRequest() {
        return isPerformingRequest;
    }

    public void setPerformingRequest(boolean performingRequest) {
        isPerformingRequest = performingRequest;
    }

}
