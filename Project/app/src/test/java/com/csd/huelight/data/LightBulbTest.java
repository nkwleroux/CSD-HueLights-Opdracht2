package com.csd.huelight.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LightBulbTest {

    @Test
    void getUID() {
        String UID = "UID";
        LightBulb lightBulb = new LightBulb(UID, "", true, (short)0, (byte)0 ,(byte)0, false);
        assertEquals(UID, lightBulb.getUID());
    }

    @Test
    void getName() {
    }

    @Test
    void setName() {
    }

    @Test
    void isOn() {
    }

    @Test
    void setOn() {
    }

    @Test
    void getHue() {
    }

    @Test
    void setHue() {
    }

    @Test
    void getSaturation() {
    }

    @Test
    void setSaturation() {
    }

    @Test
    void getBrightness() {
    }

    @Test
    void setBrightness() {
    }

    @Test
    void isColorLoop() {
    }

    @Test
    void setColorLoop() {
    }
}