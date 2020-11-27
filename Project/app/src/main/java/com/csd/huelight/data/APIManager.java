package com.csd.huelight.data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIManager extends Observable {

    //TODO Dummy data. Need to implement.
    //Uses singleton pattern

    private static final String LOGTAG = APIManager.class.getName();

    private static APIManager apiManagerInstance;
    private OkHttpClient client;

    private MutableLiveData<List<LightBulb>> _lightBulbs;

    private String ip = "192.168.178.25";
    private int port = 8000;
    private String username = "newdeveloper";

    public static APIManager getInstance() {
        if (apiManagerInstance == null) {
            apiManagerInstance = new APIManager();
        }
        return apiManagerInstance;
    }

    private APIManager() {
        this.client = new OkHttpClient();
        this._lightBulbs = new MutableLiveData<>(new ArrayList<>());
    }

    //supposed data retrieval
    public LiveData<List<LightBulb>> getLiveDataLightBulbs() {
        return _lightBulbs;
    }

    public List<LightBulb> getLightBulbs(){
        return _lightBulbs.getValue();
    }

    private String getHTTRequest(){
        return "http://" + ip + ":" + port + "/api/" + username;
    }

    public void retrieveLightBulbs(){
        Request request = new Request.Builder()
                .url(getHTTRequest() + "/lights")
                .build();

        this.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(LOGTAG, "http failure", e);
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String jsonString = response.body().string();
                    Log.d(LOGTAG, jsonString);
                    try {
                        JSONObject root = new JSONObject(jsonString);
                        JSONArray names = root.names();
                        List<LightBulb> lightBulbs = new ArrayList<>();
                        for (int i = 0; i < names.length(); i++){
                            JSONObject lightBulbJson = root.getJSONObject(names.getString(i));
                            JSONObject lightBulbStateJson = lightBulbJson.getJSONObject("state");
                            lightBulbs.add(new LightBulb(
                                    lightBulbJson.getString("uniqueid"),
                                    lightBulbJson.getString("name"),
                                    lightBulbStateJson.getBoolean("on"),
                                    (short) lightBulbStateJson.getInt("hue"),
                                    (byte) lightBulbStateJson.getInt("sat"),
                                    (byte) lightBulbStateJson.getInt("bri"),
                                    (lightBulbStateJson.getString("effect").equals("colorloop"))));
                        }

                        _lightBulbs.postValue(lightBulbs);
                        setChanged();
                        notifyObservers();
                    } catch (JSONException e) {
                        Log.e(LOGTAG, "Could not parse malformed JSON: \"" + jsonString + "\"", e);                    }
                }
            }
        });
    }

    public List<LightBulb> getRandomLightBulbs(int amount) {
        List<LightBulb> lightBulbs = new ArrayList<>();
        Random random = new Random();
        for (int count = 1; count <=amount; count++) {
            lightBulbs.add(new LightBulb(
                    "UID" + random.nextInt(),
                    "LightBulb " + count,
                    random.nextBoolean(),
                    (short) random.nextInt(),
                    (byte) random.nextInt(),
                    (byte) random.nextInt(),
                    false));
        }
        return lightBulbs;
    }
}
