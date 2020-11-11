package com.example.mvvmsampleapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmsampleapp.R;
import com.example.mvvmsampleapp.models.Recipe;

import java.util.List;

//Generic ViewHolder
public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /* O Adapter é um intermediário entre a RecylcerView e os dados. Ele pega nos dados e colaca-os
     * na view e informa a RecyclerView onde, como e quando mostrar os dados. */

    private List<Recipe> recipes;
    private OnRecipeListener onRecipeListener;

    public RecipeRecyclerAdapter(List<Recipe> recipes, OnRecipeListener onRecipeListener) {
        this.recipes = recipes;
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //É chamado quando um novo 'ViewHolder' é preciso, ou seja,
        //quando não existe um item que se possa fazer reciclagem do mesmo, (por já ter aparecido), ele é chamado para criar esse novo iten.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
        return new RecipeViewHolder(view, onRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Função chamada pela RecyclerView para mostrar a informação num determinado sitio.
        ((RecipeViewHolder)holder).recipe_title.setText(recipes.get(position).getTitle());
        ((RecipeViewHolder)holder).recipe_publisher.setText(recipes.get(position).getPublisher());
        ((RecipeViewHolder)holder).recipe_social_score.setText(String.valueOf(Math.round(recipes.get(position).getSocial_rank())));
    }

    @Override
    public int getItemCount() {
        //Responsável por dizer á RecyclerView quantos itens serão mostrados;
        return recipes.size();
    }

    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }

}
