package com.example.mvvmsampleapp.requests.responses;

import com.example.mvvmsampleapp.models.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeResponse {

    //Retrofit job
    //1 step - findind the 'recipe' object
    @SerializedName("recipe")
    //2 step
    @Expose() //to gson convert can deserialize or serialize the data from the reponse
    private Recipe recipe;

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "recipe=" + recipe +
                '}';
    }

}
