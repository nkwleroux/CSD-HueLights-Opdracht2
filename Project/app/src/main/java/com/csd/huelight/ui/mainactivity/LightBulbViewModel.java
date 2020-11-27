package com.csd.huelight.ui.mainactivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.csd.huelight.data.APIManager;
import com.csd.huelight.data.LightBulb;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

//View models should not hold reference to context/activity/fragment/view

public class LightBulbViewModel extends ViewModel implements Observer {
    // TODO: Implement the ViewModel


    private MutableLiveData<List<LightBulb>> lightBulbs;
    private APIManager apiManager;

    public void init() {
        if (lightBulbs != null) {
            return;
        }
        apiManager = apiManager.getInstance();
        apiManager.retrieveLightBulbs();
        lightBulbs = new MutableLiveData<>(new ArrayList<>());
        apiManager.addObserver(this::update);
    }

    public LiveData<List<LightBulb>> getLightBulbs() {
        return lightBulbs;
    }

    //TODO
    @Override
    protected void onCleared() {
        super.onCleared();
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable.getClass() == APIManager.class) {
            lightBulbs.postValue(((APIManager) observable).getLightBulbs());
        }
    }
}