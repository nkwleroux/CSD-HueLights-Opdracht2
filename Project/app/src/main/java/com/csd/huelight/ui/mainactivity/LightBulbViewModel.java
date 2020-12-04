package com.csd.huelight.ui.mainactivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.csd.huelight.Util.Observable;
import com.csd.huelight.Util.Observer;
import com.csd.huelight.data.APIManager;
import com.csd.huelight.data.LightBulb;
import com.csd.huelight.data.ObservableLightBulbApiManager;

import java.util.ArrayList;
import java.util.List;

//View models should not hold reference to context/activity/fragment/view

public class LightBulbViewModel extends ViewModel implements Observer {

    private static final String LOGTAG = LightBulbViewModel.class.getName();

    private MutableLiveData<List<LightBulb>> lightBulbs;
    private MutableLiveData<Byte> calls;
    private MutableLiveData<String> exceptionMessage;
    private ObservableLightBulbApiManager apiManager;

    public void init(ObservableLightBulbApiManager apiManager) {
        if (lightBulbs != null) {
            return;
        }
        this.apiManager = apiManager;
        this.apiManager.addObserver(this);
        this.lightBulbs = new MutableLiveData<>(new ArrayList<>());
        this.calls = new MutableLiveData<>((byte) 0);
        this.exceptionMessage = new MutableLiveData<>("");
        this.apiManager.retrieveLightBulbs();
    }

    public LiveData<List<LightBulb>> getLightBulbs() {
        return lightBulbs;
    }

    public LiveData<Byte> getCalls() {
        return calls;
    }

    public LiveData<String> getExceptionMessage() {
        return exceptionMessage;
    }

    @Override
    public void update(Observable source) {
        if (source instanceof APIManager) {
            APIManager apiManager = (APIManager) source;

            if (apiManager.getException() != null && apiManager.getException().getMessage() != this.exceptionMessage.getValue()) {
//                    Log.e(LOGTAG, "observed exception", apiManager.getException());
                this.exceptionMessage.postValue(apiManager.getException().getMessage());
            }
            if (apiManager.getLightBulbs() != this.lightBulbs.getValue()) {
                this.lightBulbs.postValue(apiManager.getLightBulbs());
            }
            if (apiManager.getCalls() != this.calls.getValue()) {
                this.calls.postValue(apiManager.getCalls());
            }
        }
    }

    public void retrieveLightBulbs() {
        this.apiManager.retrieveLightBulbs();
    }

    public void setLightBulbState(LightBulb lightBulb) {
        this.apiManager.setLightBulbState(lightBulb);
    }

    public void setLightBulbName(LightBulb lightBulb){
        this.apiManager.setLightBulbName(lightBulb);
    }
}