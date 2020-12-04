package com.csd.huelight.ui.mainactivity;

import com.csd.huelight.data.APIManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class LightBulbViewModelIntegrationTest {
    final MockWebServer mockWebServer = new MockWebServer();

    private LightBulbViewModel lightBulbViewModel = new LightBulbViewModel();


    @BeforeEach
    void setUp() throws IOException {
        mockWebServer.start();
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    // test failed omdat het niet in een android omgeving zit
    //TODO InstantTaskExecutorRule
    @Test
    void retrieveLightBulbsTest() throws InterruptedException {
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\n" +
                        "    \"1\": {\n" +
                        "        \"modelid\": \"LCT001\",\n" +
                        "        \"name\": \"Hue lamp 1\",\n" +
                        "        \"swversion\": \"65003148\",\n" +
                        "        \"state\": {\n" +
                        "            \"xy\": [\n" +
                        "                0,\n" +
                        "                0\n" +
                        "            ],\n" +
                        "            \"ct\": 0,\n" +
                        "            \"alert\": \"none\",\n" +
                        "            \"sat\": 254,\n" +
                        "            \"effect\": \"colorloop\",\n" +
                        "            \"bri\": 254,\n" +
                        "            \"hue\": 65535,\n" +
                        "            \"colormode\": \"hs\",\n" +
                        "            \"reachable\": true,\n" +
                        "            \"on\": true\n" +
                        "        },\n" +
                        "        \"type\": \"Extended color light\",\n" +
                        "        \"pointsymbol\": {\n" +
                        "            \"1\": \"none\",\n" +
                        "            \"2\": \"none\",\n" +
                        "            \"3\": \"none\",\n" +
                        "            \"4\": \"none\",\n" +
                        "            \"5\": \"none\",\n" +
                        "            \"6\": \"none\",\n" +
                        "            \"7\": \"none\",\n" +
                        "            \"8\": \"none\"\n" +
                        "        },\n" +
                        "        \"uniqueid\": \"00:17:88:01:00:d4:12:08-0a\"\n" +
                        "    },\n" +
                        "    \"2\": {\n" +
                        "        \"modelid\": \"LCT001\",\n" +
                        "        \"name\": \"Hue Lamp 2\",\n" +
                        "        \"swversion\": \"65003148\",\n" +
                        "        \"state\": {\n" +
                        "            \"xy\": [\n" +
                        "                0.346,\n" +
                        "                0.3568\n" +
                        "            ],\n" +
                        "            \"ct\": 201,\n" +
                        "            \"alert\": \"none\",\n" +
                        "            \"sat\": 144,\n" +
                        "            \"effect\": \"colorloop\",\n" +
                        "            \"bri\": 254,\n" +
                        "            \"hue\": 20984,\n" +
                        "            \"colormode\": \"hs\",\n" +
                        "            \"reachable\": true,\n" +
                        "            \"on\": true\n" +
                        "        },\n" +
                        "        \"type\": \"Extended color light\",\n" +
                        "        \"pointsymbol\": {\n" +
                        "            \"1\": \"none\",\n" +
                        "            \"2\": \"none\",\n" +
                        "            \"3\": \"none\",\n" +
                        "            \"4\": \"none\",\n" +
                        "            \"5\": \"none\",\n" +
                        "            \"6\": \"none\",\n" +
                        "            \"7\": \"none\",\n" +
                        "            \"8\": \"none\"\n" +
                        "        },\n" +
                        "        \"uniqueid\": \"00:17:88:01:00:d4:12:08-0b\"\n" +
                        "    },\n" +
                        "    \"3\": {\n" +
                        "        \"modelid\": \"LCT001\",\n" +
                        "        \"name\": \"Hue Lamp 3\",\n" +
                        "        \"swversion\": \"65003148\",\n" +
                        "        \"state\": {\n" +
                        "            \"xy\": [\n" +
                        "                0.346,\n" +
                        "                0.3568\n" +
                        "            ],\n" +
                        "            \"ct\": 201,\n" +
                        "            \"alert\": \"none\",\n" +
                        "            \"sat\": 254,\n" +
                        "            \"effect\": \"colorloop\",\n" +
                        "            \"bri\": 254,\n" +
                        "            \"hue\": 45005,\n" +
                        "            \"colormode\": \"hs\",\n" +
                        "            \"reachable\": true,\n" +
                        "            \"on\": true\n" +
                        "        },\n" +
                        "        \"type\": \"Extended color light\",\n" +
                        "        \"pointsymbol\": {\n" +
                        "            \"1\": \"none\",\n" +
                        "            \"2\": \"none\",\n" +
                        "            \"3\": \"none\",\n" +
                        "            \"4\": \"none\",\n" +
                        "            \"5\": \"none\",\n" +
                        "            \"6\": \"none\",\n" +
                        "            \"7\": \"none\",\n" +
                        "            \"8\": \"none\"\n" +
                        "        },\n" +
                        "        \"uniqueid\": \"00:17:88:01:00:d4:12:08-0c\"\n" +
                        "    }\n" +
                        "}"));

        lightBulbViewModel.init(APIManager.getInstance());


        int i = 0;
        while (mockWebServer.getRequestCount() == 0) {
            Thread.sleep(100);
            i++;
            if (i > 20) {
                break;
            }
        }

        assertNotNull(lightBulbViewModel.getExceptionMessage());
        assertEquals((byte) lightBulbViewModel.getCalls().getValue(), 0);
        assertEquals(lightBulbViewModel.getLightBulbs().getValue().size(), 3);
    }
}
