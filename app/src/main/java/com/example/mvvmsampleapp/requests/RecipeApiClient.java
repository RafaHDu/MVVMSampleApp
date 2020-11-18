package com.example.mvvmsampleapp.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmsampleapp.AppExecuters;
import com.example.mvvmsampleapp.models.Recipe;
import com.example.mvvmsampleapp.requests.responses.RecipeResponse;
import com.example.mvvmsampleapp.requests.responses.RecipeSearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.mvvmsampleapp.util.Constants.NETWORK_TIMEOUT;

public class RecipeApiClient {

    //Here is where we will make the requests to the server to get the data.
    //Its the RemoteDataSource (Diagram) UNDER the Repository
    //Retrofit will access the data and then will submit to the LiveData and it will trick it down to the View

    private static final String TAG = "RecipeApiClient";
    private RetrieveRecipesRunnable mRetrieveRecipesRunnable;
    private RetrieveRecipeRunnable mRetrieveRecipeRunnable;

    //Singleton
    private static RecipeApiClient instance;
    public static RecipeApiClient getInstance() {
        if(instance == null){
            instance = new RecipeApiClient();
        }
        return instance;
    }

    private MutableLiveData<List<Recipe>> mRecipes;
    private MutableLiveData<Recipe> mRecipe;


    private RecipeApiClient() {
        mRecipes = new MutableLiveData<>();
        mRecipe = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }

    public LiveData<Recipe> getRecipe(){
        return mRecipe;
    }

    public void searchRecipeApi(String query, int pageNumber){
        if (mRetrieveRecipesRunnable != null){
            //if the query as been executed, it's needed to reset the query  and
            //instanciate a new one to make a new query, so set to null 1st
            mRetrieveRecipesRunnable = null;
        }
        mRetrieveRecipesRunnable = new RetrieveRecipesRunnable(query, pageNumber);

        //we want to schedule cuz we want to be able to stop it if we need it (timeout)
        final Future handler = AppExecuters.getInstance().networkIO().submit(mRetrieveRecipesRunnable);

        AppExecuters.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Will run after a set amount of time (TimeOut)
                handler.cancel(true); //will stop the request
                //TODO: Let the user know its timed out
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void searchRecipeById(String rId){
        if (mRetrieveRecipeRunnable != null){
            //if the query as been executed, it's needed to reset the query  and
            //instanciate a new one to make a new query, so set to null 1st
            mRetrieveRecipeRunnable = null;
        }
        mRetrieveRecipeRunnable = new RetrieveRecipeRunnable(rId);

        //we want to schedule cuz we want to be able to stop it if we need it (timeout)
        final Future handler = AppExecuters.getInstance().networkIO().submit(mRetrieveRecipeRunnable);

        AppExecuters.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Will run after a set amount of time (TimeOut)
                handler.cancel(true); //will stop the request
                //TODO: Let the user know its timed out
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveRecipesRunnable implements Runnable{

        private String query;
        private int pageNumber;
        private boolean cancelRequest; //Stops the runnable if times out or user wants to cancel manually

        public RetrieveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //responsible for the search query
            try {
                Response response = getRecipes(query, pageNumber).execute();
                if (cancelRequest){
                    return;
                }
                if(response.code() == 200) {
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse)response.body()).getRecipes());
                    if(pageNumber == 1){
                        //set values to LiveData.
                        //setValue() is to set a value that is not on the background thread
                        //postValue() is for on the background thread (here we are on the BG thread)
                        mRecipes.postValue(list);
                    } else {
                        //append the results
                        List<Recipe> currentRecipes = mRecipes.getValue();
                        currentRecipes.addAll(list);
                        mRecipes.postValue(list);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mRecipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mRecipes.postValue(null);
            }
        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber){
            return ServiceGenerator.getRecipeApi().searchRecipe(query, String.valueOf(pageNumber));
        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling the search request.");
            cancelRequest = true;
        }

    }

    private class RetrieveRecipeRunnable implements Runnable{

        private String rId;
        private boolean cancelRequest; //Stops the runnable if times out or user wants to cancel manually

        public RetrieveRecipeRunnable(String rId) {
            this.rId = rId;
            cancelRequest = false;
        }

        @Override
        public void run() {
            //responsible for the get query
            try {
                Response response = getRecipe(rId).execute();
                if (cancelRequest){
                    return;
                }
                if(response.code() == 200) {
                    Recipe recipe = ((RecipeResponse)response.body()).getRecipe();
                    mRecipe.postValue(recipe);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mRecipe.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mRecipe.postValue(null);
            }
        }

        private Call<RecipeResponse> getRecipe(String rId){
            return ServiceGenerator.getRecipeApi().getRecipe(rId);
        }

        private void cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling the search request.");
            cancelRequest = true;
        }

    }

    public void cancelRequest(){
        if(mRetrieveRecipesRunnable != null){
            mRetrieveRecipesRunnable.cancelRequest();
        } else if(mRetrieveRecipeRunnable != null){
            mRetrieveRecipeRunnable.cancelRequest();
        }
    }

}
