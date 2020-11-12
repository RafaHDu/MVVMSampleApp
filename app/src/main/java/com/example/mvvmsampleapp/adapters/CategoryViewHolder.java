package com.example.mvvmsampleapp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmsampleapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CircleImageView category_image;
    TextView category_title;
    OnRecipeListener onRecipeListener;

    public CategoryViewHolder(@NonNull View itemView, OnRecipeListener onRecipeListener) {
        super(itemView);
        this.onRecipeListener = onRecipeListener;
        this.category_image = itemView.findViewById(R.id.category_image);
        this.category_title = itemView.findViewById(R.id.category_title);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onRecipeListener.onCategoryClick(category_title.getText().toString());
    }

}
