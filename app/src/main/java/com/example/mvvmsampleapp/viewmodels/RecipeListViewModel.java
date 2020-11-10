package com.example.mvvmsampleapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmsampleapp.Repositories.RecipeRepository;
import com.example.mvvmsampleapp.models.Recipe;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    //This VieModel will be responsible to getting/holding/retrieving the recipes that will be displayed in the application

    private RecipeRepository recipeRepository;

    public RecipeListViewModel() {
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return recipeRepository.getRecipes();
    }

}
