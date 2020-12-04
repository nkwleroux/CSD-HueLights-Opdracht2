package com.csd.huelight.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static com.csd.huelight.data.APIManager.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class APIManagerTest {

    static APIManager apiManagerInstance;
    static List<LightBulb> mockList;
    static MockWebServer mockWebServer;
    static HttpUrl baseUrl;
    static String responseMessage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        apiManagerInstance = APIManager.getInstance();
        mockList = new ArrayList<>();
        mockList.add(new LightBulb(
                "00:17:88:01:00:bd:c7:b9-0b", "1", "Hue color lamp 7", false,
                33761, (short) 254, (short) 1, false));

        try {
            mockWebServer = new MockWebServer();
            mockWebServer.enqueue(new MockResponse().setResponseCode(200).setBody(sampleResponseFromHueEmulatorWithoutError));
            mockWebServer.start();
            baseUrl = mockWebServer.url("/api/newdeveloper/lights");

            //Makes a fake request to the mock server.
            RequestBody requestBody = RequestBody.create(sampleResponseFromHueEmulatorWithoutError, JSON);
            okhttp3.Request request = new Request.Builder().post(requestBody).url(baseUrl).build();
            Response response = new OkHttpClient().newCall(request).execute();
            responseMessage = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void cleanUp() {
        try {
            mockWebServer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetInstance() {
        assertEquals(APIManager.getInstance(), apiManagerInstance);
    }

    @Test
    void testGetAndSetLightBulbs() {
        apiManagerInstance.setLightBulbs(mockList);
        assertEquals(mockList, apiManagerInstance.getLightBulbs());
    }

    //TODO Weet niet hoe ik dit moet testen.
    /*@Test
    void testGetException(){
    }*/

    //TODO Weet niet hoe ik dit moet testen.
/*    @Test
    void testGetCalls(){

    }*/

    @Test
    void testGettersAndSetters() {

        apiManagerInstance.setIp("127.0.0.1");
        apiManagerInstance.setPort(mockWebServer.getPort());
        apiManagerInstance.setUsername("newdeveloper");

        assertEquals("127.0.0.1", apiManagerInstance.getIp());
        assertEquals(mockWebServer.getPort(), apiManagerInstance.getPort());
        assertEquals("newdeveloper", apiManagerInstance.getUsername());
    }

    @Test
    void testWebServerURL() {
        String ip = "127.0.0.1";
        String port = String.valueOf(mockWebServer.getPort());
        String mockURL = "http://" + ip + ":" + port + "/api/newdeveloper/lights";

        apiManagerInstance.setIp(ip);
        apiManagerInstance.setPort(mockWebServer.getPort());
        apiManagerInstance.setUsername("newdeveloper");

        //Checks if the url is correct.
        assertEquals(mockURL, apiManagerInstance.getHTTRequest() + "/lights");
        assertEquals(mockURL, baseUrl.url().toString());
        assertEquals(apiManagerInstance.getHTTRequest() + "/lights", baseUrl.url().toString());
    }

    @Test
    void testSampleDataFromServer() {
        //Checks if the data sent from the server is the same as the sample data.
        assertEquals(sampleResponseFromHueEmulatorWithoutError, responseMessage);
    }

    @Test
    void testRetrieveLightBulbs() {
        try {
            JSONObject root = new JSONObject(responseMessage);
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
                        lightBulbStateJson.getInt("hue"),
                        (short) lightBulbStateJson.getInt("sat"),
                        (short) lightBulbStateJson.getInt("bri"),
                        (lightBulbStateJson.getString("effect").equals("colorloop"))));
            }

            apiManagerInstance.setIp("127.0.0.1");
            apiManagerInstance.setPort(8000);
            apiManagerInstance.setUsername("newdeveloper");
            apiManagerInstance.retrieveLightBulbs();

            /*APIManager manager = mock(APIManager.class);
            manager.setIp("127.0.0.1");
            manager.setPort(8000);
            manager.setUsername("newdeveloper");
            manager.retrieveLightBulbs();
            assertEquals(lightBulbs, manager.getLightBulbs());
             */

            assertEquals(lightBulbs, apiManagerInstance.getLightBulbs());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //TODO Weet niet hoe ik dit moet testen.
/*    @Test
    void setLightBulbState() {

    }*/

    static String sampleResponseFromHueEmulatorWithoutError = "{\n" +
            "\"1\": {\n" +
            "        \"state\": {\n" +
            "            \"on\": false,\n" +
            "            \"bri\": 1,\n" +
            "            \"hue\": 33761,\n" +
            "            \"sat\": 254,\n" +
            "            \"effect\": \"none\",\n" +
            "            \"xy\": [\n" +
            "                0.3171,\n" +
            "                0.3366\n" +
            "            ],\n" +
            "            \"ct\": 159,\n" +
            "            \"alert\": \"none\",\n" +
            "            \"colormode\": \"xy\",\n" +
            "            \"mode\": \"homeautomation\",\n" +
            "            \"reachable\": true\n" +
            "        },\n" +
            "        \"swupdate\": {\n" +
            "            \"state\": \"noupdates\",\n" +
            "            \"lastinstall\": \"2018-01-02T19:24:20\"\n" +
            "        },\n" +
            "        \"type\": \"Extended color light\",\n" +
            "        \"name\": \"Hue color lamp 7\",\n" +
            "        \"modelid\": \"LCT007\",\n" +
            "        \"manufacturername\": \"Philips\",\n" +
            "        \"productname\": \"Hue color lamp\",\n" +
            "        \"capabilities\": {\n" +
            "            \"certified\": true,\n" +
            "            \"control\": {\n" +
            "                \"mindimlevel\": 5000,\n" +
            "                \"maxlumen\": 600,\n" +
            "                \"colorgamuttype\": \"B\",\n" +
            "                \"colorgamut\": [\n" +
            "                    [\n" +
            "                        0.675,\n" +
            "                        0.322\n" +
            "                    ],\n" +
            "                    [\n" +
            "                        0.409,\n" +
            "                        0.518\n" +
            "                    ],\n" +
            "                    [\n" +
            "                        0.167,\n" +
            "                        0.04\n" +
            "                    ]\n" +
            "                ],\n" +
            "                \"ct\": {\n" +
            "                    \"min\": 153,\n" +
            "                    \"max\": 500\n" +
            "                }\n" +
            "            },\n" +
            "            \"streaming\": {\n" +
            "                \"renderer\": true,\n" +
            "                \"proxy\": false\n" +
            "            }\n" +
            "        },\n" +
            "        \"config\": {\n" +
            "            \"archetype\": \"sultanbulb\",\n" +
            "            \"function\": \"mixed\",\n" +
            "            \"direction\": \"omnidirectional\"\n" +
            "        },\n" +
            "        \"uniqueid\": \"00:17:88:01:00:bd:c7:b9-0b\",\n" +
            "        \"swversion\": \"5.105.0.21169\"\n" +
            "    }\n" +
            "}";

    //geen tijd om te gebruiken voor tests.
    static String sampleResponseFromHueEmulatorWithError = "{\n" +
            "\"1\": {\n" +
            "        \"state\": {\n" +
            "            \"onn\": false,\n" + //Dit is fout. Moet on zijn.
            "            \"bri\": 1,\n" +
            "            \"hue\": 33761,\n" +
            "            \"sat\": 254,\n" +
            "            \"effect\": \"none\",\n" +
            "            \"xy\": [\n" +
            "                0.3171,\n" +
            "                0.3366\n" +
            "            ],\n" +
            "            \"ct\": 159,\n" +
            "            \"alert\": \"none\",\n" +
            "            \"colormode\": \"xy\",\n" +
            "            \"mode\": \"homeautomation\",\n" +
            "            \"reachable\": true\n" +
            "        },\n" +
            "        \"swupdate\": {\n" +
            "            \"state\": \"noupdates\",\n" +
            "            \"lastinstall\": \"2018-01-02T19:24:20\"\n" +
            "        },\n" +
            "        \"type\": \"Extended color light\",\n" +
            "        \"name\": \"Hue color lamp 7\",\n" +
            "        \"modelid\": \"LCT007\",\n" +
            "        \"manufacturername\": \"Philips\",\n" +
            "        \"productname\": \"Hue color lamp\",\n" +
            "        \"capabilities\": {\n" +
            "            \"certified\": true,\n" +
            "            \"control\": {\n" +
            "                \"mindimlevel\": 5000,\n" +
            "                \"maxlumen\": 600,\n" +
            "                \"colorgamuttype\": \"B\",\n" +
            "                \"colorgamut\": [\n" +
            "                    [\n" +
            "                        0.675,\n" +
            "                        0.322\n" +
            "                    ],\n" +
            "                    [\n" +
            "                        0.409,\n" +
            "                        0.518\n" +
            "                    ],\n" +
            "                    [\n" +
            "                        0.167,\n" +
            "                        0.04\n" +
            "                    ]\n" +
            "                ],\n" +
            "                \"ct\": {\n" +
            "                    \"min\": 153,\n" +
            "                    \"max\": 500\n" +
            "                }\n" +
            "            },\n" +
            "            \"streaming\": {\n" +
            "                \"renderer\": true,\n" +
            "                \"proxy\": false\n" +
            "            }\n" +
            "        },\n" +
            "        \"config\": {\n" +
            "            \"archetype\": \"sultanbulb\",\n" +
            "            \"function\": \"mixed\",\n" +
            "            \"direction\": \"omnidirectional\"\n" +
            "        },\n" +
            "        \"uniqueid\": \"00:17:88:01:00:bd:c7:b9-0b\",\n" +
            "        \"swversion\": \"5.105.0.21169\"\n" +
            "    }\n" +
            "}";
}