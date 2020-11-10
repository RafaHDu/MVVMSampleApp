package com.example.mvvmsampleapp;

import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mvvmsampleapp.models.Recipe;
import com.example.mvvmsampleapp.requests.RecipeApi;
import com.example.mvvmsampleapp.requests.ServiceGenerator;
import com.example.mvvmsampleapp.requests.responses.RecipeResponse;
import com.example.mvvmsampleapp.requests.responses.RecipeSearchResponse;
import com.example.mvvmsampleapp.viewmodels.RecipeListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {

    private Button button;
    private static final String TAG = "RecipeListActivity";

    private RecipeListViewModel recipeListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);

    }

    private void subscribeObservers(){
        recipeListViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {

            }
        });
    }

    private void testRetrofitRecipeSearch(){
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();
        Call<RecipeSearchResponse> responseCall = recipeApi.searchRecipe("chicken", "1");

        responseCall.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                if(response.code() == 200) {
                    Log.d(TAG, "onResponse: server response: " + response.toString());
                    List<Recipe> recipes = new ArrayList<>(response.body().getRecipes()); //will cast the response into a list object type
                    for(Recipe recipe : recipes){
                        Log.d(TAG, "onResponse: " + recipe.getTitle());
                    }
                } else {
                    //Response was successful but wasn't an 200 code
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {

            }
        });

    }

    private void testRetrofiRecipe(){
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();
        Call<RecipeResponse> responseCall = recipeApi.getRecipe("49421");

        responseCall.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if(response.code() == 200) {
                    Log.d(TAG, "onResponse: server response: " + response.toString());
                    Recipe recipe = response.body().getRecipe(); //will cast the response into a list object type
                    Log.d(TAG, "onResponse: RECIPE: " + recipe.toString());
                } else {
                    //Response was successful but wasn't an 200 code
                    try {
                        Log.d(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {

            }
        });

    }

}