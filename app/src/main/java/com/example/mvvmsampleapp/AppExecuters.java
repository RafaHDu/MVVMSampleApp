package com.example.mvvmsampleapp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecuters {

    //Class to fire retrofit requests in background threads

    //Singleton
    private static AppExecuters instance;
    public static AppExecuters getInstance(){
        if(instance == null){
            instance = new AppExecuters();
        }
        return instance;
    }

    //It's an executor (thing that use to run runnable tasks) service that can schedule commands to run after a given delay.
    //This will be used to execute tasks with retrofit but with an TimeOut
    private final ScheduledExecutorService mNetworkIO = Executors.newScheduledThreadPool(3);

    public ScheduledExecutorService networkIO(){
        return mNetworkIO;
    }
}
