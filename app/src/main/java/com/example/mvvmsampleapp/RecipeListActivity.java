package com.example.mvvmsampleapp;

import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecipeListActivity extends BaseActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mProgressBar.getVisibility() == View.GONE){
                    showProgressBar(true);
                } else{
                    showProgressBar(false);
                }
            }
        });

    }
}