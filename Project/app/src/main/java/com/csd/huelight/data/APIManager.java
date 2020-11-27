package com.csd.huelight.data;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class APIManager {

    //TODO Dummy data. Need to implement.
    //Uses singleton pattern

    private static APIManager apiManagerInstance;
    private ArrayList<LightBulb> lightBulbs;

    public static APIManager getInstance() {
        if (apiManagerInstance == null) {
            apiManagerInstance = new APIManager();
        }
        return apiManagerInstance;
    }

    //supposed data retrieval
    public MutableLiveData<List<LightBulb>> getLightBulbs() {
        setLightBulbs();
        MutableLiveData<List<LightBulb>> data = new MutableLiveData<>();
        data.setValue(lightBulbs);
        return data;
    }

    private void setLightBulbs() {
        lightBulbs = new ArrayList<>();
        Random random = new Random();
        for (int count = 1; count <= 5; count++) {
            lightBulbs.add(new LightBulb(
                    "UID : " + Math.abs(random.nextInt()),
                    "LightBulb " + count,
                    random.nextBoolean(),
                    (int) Math.abs(random.nextInt(65535)),
                    (short) Math.abs(random.nextInt(254)),
                    (short) Math.abs(random.nextInt(254)),
                    random.nextBoolean()));
        }
    }
}
