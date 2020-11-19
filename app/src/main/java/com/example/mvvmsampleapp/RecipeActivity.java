package com.example.mvvmsampleapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvvmsampleapp.adapters.RecipeViewHolder;
import com.example.mvvmsampleapp.models.Recipe;
import com.example.mvvmsampleapp.viewmodels.RecipeListViewModel;
import com.example.mvvmsampleapp.viewmodels.RecipeViewModel;

import java.util.Arrays;
import java.util.List;

public class RecipeActivity extends BaseActivity {

    private ScrollView parent;
    private AppCompatImageView recipe_image;
    private TextView recipe_title, recipe_social_score;
    private LinearLayout ingredients_container;

    private String[] ingredient = {"Arros", "Alho", "Ovos", "Batata", "Oreo", "Bolachinha", "Chocolate", "Baunilha"};
    private RecipeViewModel recipeViewModel;
    private static final String TAG = "RecipeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        parent = (ScrollView) findViewById(R.id.parent);
        recipe_image = (AppCompatImageView) findViewById(R.id.recipe_image);
        recipe_title = (TextView) findViewById(R.id.recipe_title);
        recipe_social_score = (TextView) findViewById(R.id.recipe_social_score);
        ingredients_container = (LinearLayout) findViewById(R.id.ingredients_container);

        showProgressBar(true);
        subscribeObservers();
        getIncomingIntent();
    }

    private void subscribeObservers(){
        recipeViewModel.getRecipe().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                if (recipe != null){
                    //cuz can be null if net fails
                    //Log.d(TAG, "onChanged: ---------------------------------------------------");
                   // Log.d(TAG, "onChanged: " + recipe.getTitle());
//                    for(String ingredient : recipe.getIngredients()){
//                        Log.d(TAG, "" + ingredient);
//                    }

                    //Need to do this check cuz when activity gets destroyed the data keeps living in the ViewModel
                    if(recipe.getRecipe_id().equals(recipeViewModel.getRecipeId())){
                        //API don't have the ingredients
                        recipe.setIngredients(ingredient);
                        //Log.d(TAG, "onChanged: " + Arrays.toString(recipe.getIngredients()));
                        setRecipesProperties(recipe);
                    }
                }
            }
        });
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("recipe")){
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            Log.d(TAG, "getIncomingIntent: " + recipe.getTitle());
            recipeViewModel.searchRecipeById(recipe.getRecipe_id());
        }
    }

    private void setRecipesProperties(Recipe recipe){
        if(recipe != null){
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
            Glide.with(this).setDefaultRequestOptions(requestOptions).load(recipe.getImage_url()).into(recipe_image);
            recipe_title.setText(recipe.getTitle());
            recipe_social_score.setText(String.valueOf(Math.round(recipe.getSocial_rank())));

            ingredients_container.removeAllViews();
            for(String ingredient : recipe.getIngredients()){
                TextView textView = new TextView(this);
                textView.setText(ingredient);
                textView.setTextSize(15);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                ingredients_container.addView(textView);
            }
        }
        showParent();
        showProgressBar(false);
    }

    private void showParent(){
        parent.setVisibility(View.VISIBLE);
    }

}
