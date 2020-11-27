package com.csd.huelight.data;

import android.util.Log;

import com.csd.huelight.Util.Observable;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class APIManager extends Observable {

    //TODO Dummy data. Need to implement.
    //Uses singleton pattern

    private static final String LOGTAG = APIManager.class.getName();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static APIManager apiManagerInstance;
    private OkHttpClient client;

    private List<LightBulb> _lightBulbs;

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
        this._lightBulbs = new ArrayList<>();
    }

    public List<LightBulb> getLightBulbs() {
        return _lightBulbs;
    }

    private String getHTTRequest() {
        return "http://" + ip + ":" + port + "/api/" + username;
    }

    public void retrieveLightBulbs() {
        Request request = new Request.Builder()
                .url(getHTTRequest() + "/lights")
                .build();

        this.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(LOGTAG, "http failure, get bulbs", e);
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String jsonString = response.body().string();
                    Log.d(LOGTAG, jsonString);
                    try {
                        JSONObject root = new JSONObject(jsonString);
                        JSONArray names = root.names();
                        List<LightBulb> lightBulbs = new ArrayList<>();
                        for (int i = 0; i < names.length(); i++) {
                            JSONObject lightBulbJson = root.getJSONObject(names.getString(i));
                            JSONObject lightBulbStateJson = lightBulbJson.getJSONObject("state");
                            lightBulbs.add(new LightBulb(
                                    lightBulbJson.getString("uniqueid"),
                                    names.get(i).toString(),
                                    lightBulbJson.getString("name"),
                                    lightBulbStateJson.getBoolean("on"),
                                    (short) lightBulbStateJson.getInt("hue"),
                                    (byte) lightBulbStateJson.getInt("sat"),
                                    (byte) lightBulbStateJson.getInt("bri"),
                                    (lightBulbStateJson.getString("effect").equals("colorloop"))));
                        }

                        _lightBulbs = lightBulbs;
                        notifyObservers();
                    } catch (JSONException e) {
                        Log.e(LOGTAG, "Could not parse malformed JSON: \"" + jsonString + "\"", e);
                    }
                }else {
                    //uuuuh
                }
            }
        });
    }

    public void setLightBulbState(LightBulb lightBulb) {
        Log.d(LOGTAG, "setting state " + lightBulb);
        try {
            JSONObject body = new JSONObject();
            body.put("on", lightBulb.isOn());
            if (lightBulb.isOn()) {
                body.put("hue", lightBulb.getHue());
                body.put("sat", lightBulb.getSaturation());
                body.put("bri", lightBulb.getBrightness());
            }
            body.put("effect", lightBulb.isColorLoop() ? "colorloop" : "none");

            RequestBody requestBody = RequestBody.create(body.toString(),JSON);
            Request request = new Request.Builder()
                    .url(getHTTRequest() + "/lights/" + lightBulb.getId() + "/state")
                    .put(requestBody)
                    .build();

            this.client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.d(LOGTAG, "http failure, set bulb state", e);
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    if (response.isSuccessful()){
                        Log.d(LOGTAG, "set state successful");
                        retrieveLightBulbs();
                    }else {
                        //uuuuh
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<LightBulb> getRandomLightBulbs(int amount) {
        List<LightBulb> lightBulbs = new ArrayList<>();
        Random random = new Random();
        for (int count = 1; count <= amount; count++) {
            lightBulbs.add(new LightBulb(
                    "UID : " + Math.abs(random.nextInt()),
                    "" + random.nextInt(),
                    "LightBulb " + count,
                    random.nextBoolean(),
                    (int) Math.abs(random.nextInt(65535)),
                    (short) Math.abs(random.nextInt(254)),
                    (short) Math.abs(random.nextInt(254)),
                    random.nextBoolean()));
        }
        return lightBulbs;
    }
}
