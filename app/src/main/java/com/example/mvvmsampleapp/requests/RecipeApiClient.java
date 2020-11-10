package com.example.mvvmsampleapp.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmsampleapp.AppExecuters;
import com.example.mvvmsampleapp.models.Recipe;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static com.example.mvvmsampleapp.util.Constants.NETWORK_TIMEOUT;

public class RecipeApiClient {

    //Here is where we will make the requests to the server to get the data.
    //Its the RemoteDataSource (Diagram) UNDER the Repository
    //Retrofit will access the data and then will submit to the LiveData and it will trick it down to the View

    //Singleton
    private static RecipeApiClient instance;
    public static RecipeApiClient getInstance() {
        if(instance == null){
            instance = new RecipeApiClient();
        }
        return instance;
    }

    private MutableLiveData<List<Recipe>> mRecipes;

    private RecipeApiClient() {
        mRecipes = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes(){
        return mRecipes;
    }

    public void searchRecipeApi(){
        final Future handler = AppExecuters.getInstance().networkIO().submit(new Runnable() {
            @Override
            public void run() {
                //retrieve data from rest api
            }
        });
        AppExecuters.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //Will run after a set amount of time (TimeOut)
                handler.cancel(true); //will stop the request
                //TODO: Let the user know its timed out
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

}
