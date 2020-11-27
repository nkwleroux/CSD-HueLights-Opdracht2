package com.csd.huelight.ui.mainactivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.csd.huelight.data.APIManager;
import com.csd.huelight.data.LightBulb;

import java.util.List;

//View models should not hold reference to context/activity/fragment/view

public class LightBulbViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<List<LightBulb>> lightBulbs;
    private APIManager apiManager;

    public void init(){
        if(lightBulbs != null){
            return;
        }
        apiManager = apiManager.getInstance();
        lightBulbs = apiManager.getLightBulbs();
    }

    public LiveData<List<LightBulb>> getLightBulbs(){
        return lightBulbs;
    }

    //TODO
    @Override
    protected void onCleared() {
        super.onCleared();
    }
}