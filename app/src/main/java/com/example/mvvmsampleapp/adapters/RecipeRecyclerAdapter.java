package com.example.mvvmsampleapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mvvmsampleapp.R;
import com.example.mvvmsampleapp.models.Recipe;

import java.util.ArrayList;
import java.util.List;

//Generic ViewHolder
public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /* O Adapter é um intermediário entre a RecylcerView e os dados. Ele pega nos dados e colaca-os
     * na view e informa a RecyclerView onde, como e quando mostrar os dados. */

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;

    private List<Recipe> recipes;
    private OnRecipeListener onRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener onRecipeListener) {
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //É chamado quando um novo 'ViewHolder' é preciso, ou seja,
        //quando não existe um item que se possa fazer reciclagem do mesmo, (por já ter aparecido), ele é chamado para criar esse novo iten.
        View view = null;
        switch (viewType){
            case RECIPE_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(view, onRecipeListener);
            case LOADING_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loading_list_item, parent, false);
                return new LoadingViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_list_item, parent, false);
                return new RecipeViewHolder(view, onRecipeListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //Função chamada pela RecyclerView para mostrar a informação num determinado sitio.
        int itemViewType = getItemViewType(position);
        if (itemViewType == RECIPE_TYPE){
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);
            //cuz of security configurations options, on Glide, on API 28 have changed its not able, anymore, to retrieve resources from HTTP requests, its needed to specify Network configurations file.
            //HTTPS requests are fine. https://stackoverflow.com/questions/45940861/android-8-cleartext-http-traffic-not-permitted
            Glide.with(holder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipes.get(position).getImage_url())
                    .into(((RecipeViewHolder)holder).recipe_image);

            ((RecipeViewHolder)holder).recipe_title.setText(recipes.get(position).getTitle());
            ((RecipeViewHolder)holder).recipe_publisher.setText(recipes.get(position).getPublisher());
            ((RecipeViewHolder)holder).recipe_social_score.setText(String.valueOf(Math.round(recipes.get(position).getSocial_rank())));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(recipes.get(position).getTitle().equals("LOADING...")){
            return LOADING_TYPE;
        } else {
            return RECIPE_TYPE;
        }
    }

    public void displayLoading(){
        if(!isLoading()){
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            recipes = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading(){
        if(recipes != null) {
            if(recipes.size() > 0){
                if(recipes.get(recipes.size()-1).getTitle().equals("LOADING...")){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        //Responsável por dizer á RecyclerView quantos itens serão mostrados;
        if (recipes != null){
            return recipes.size();
        }
        return 0;
    }

    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }

}
