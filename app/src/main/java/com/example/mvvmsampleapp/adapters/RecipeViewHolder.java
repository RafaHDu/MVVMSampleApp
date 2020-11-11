package com.example.mvvmsampleapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmsampleapp.R;

import org.w3c.dom.Text;

public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Responsáel por referenciar as View bem como por introduzir a informação nelas;

    TextView recipe_title, recipe_publisher, recipe_social_score;
    AppCompatImageView recipe_image;
    OnRecipeListener onRecipeListener;

    public RecipeViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);
        this.onRecipeListener = onRecipeListener;
        recipe_title = itemView.findViewById(R.id.recipe_title);
        recipe_publisher = itemView.findViewById(R.id.recipe_publisher);
        recipe_social_score = itemView.findViewById(R.id.recipe_social_score);
        recipe_image = itemView.findViewById(R.id.recipe_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onRecipeListener.OnRecipeClick(getAdapterPosition());
    }

}
