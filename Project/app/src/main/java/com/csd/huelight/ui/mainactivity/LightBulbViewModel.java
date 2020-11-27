package com.csd.huelight.ui.mainactivity;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.csd.huelight.Util.Observable;
import com.csd.huelight.Util.Observer;
import com.csd.huelight.data.APIManager;
import com.csd.huelight.data.LightBulb;

import java.util.ArrayList;
import java.util.List;

//View models should not hold reference to context/activity/fragment/view

public class LightBulbViewModel extends ViewModel implements Observer {
    // TODO: Implement the ViewModel

    private static final String LOGTAG = LightBulbViewModel.class.getName();

    private MutableLiveData<List<LightBulb>> lightBulbs;
    private APIManager apiManager;

    public void init() {
        if (lightBulbs != null) {
            return;
        }
        apiManager = apiManager.getInstance();
        apiManager.addObserver(this);
        apiManager.retrieveLightBulbs();
        lightBulbs = new MutableLiveData<>(new ArrayList<>());
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
    public void update(Observable source) {
        if (source instanceof APIManager) {
            this.lightBulbs.postValue(((APIManager) source).getLightBulbs());
        }
    }

    public void retrieveLightBulbs(){
        this.apiManager.retrieveLightBulbs();
    }

    public void setLightBulbState(LightBulb lightBulb){
        this.apiManager.setLightBulbState(lightBulb);
    }
}