package com.example.mvvmsampleapp;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.mvvmsampleapp.models.Recipe;

public class RecipeActivity extends BaseActivity {

    private ScrollView parent;
    private AppCompatImageView recipe_image;
    private TextView recipe_title, recipe_social_score;
    private LinearLayout ingredients_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        parent = (ScrollView) findViewById(R.id.parent);
        recipe_image = (AppCompatImageView) findViewById(R.id.recipe_image);
        recipe_title = (TextView) findViewById(R.id.recipe_title);
        recipe_social_score = (TextView) findViewById(R.id.recipe_social_score);
        ingredients_container = (LinearLayout) findViewById(R.id.ingredients_container);

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("recipe")){
            Recipe recipe = getIntent().getParcelableExtra("recipe");
        }
    }

}
