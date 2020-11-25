package com.csd.huelight.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LightBulbTest {

    @Test
    void testGetters() {
        //arrange
        String UID = "UID";
        String name = "name";
        boolean on = true;
        short hue = 500;
        byte saturation = 100;
        byte brightness = -100;
        boolean colorloop = false;

        //act
        LightBulb lightBulb = new LightBulb(UID, name, on, hue, saturation ,brightness, colorloop);

        //assert
        assertEquals(UID, lightBulb.getUID());
        assertEquals(name , lightBulb.getName());
        assertEquals(on , lightBulb.isOn());
        assertEquals(hue, lightBulb.getHue());
        assertEquals(saturation, lightBulb.getSaturation());
        assertEquals(brightness, lightBulb.getBrightness());
        assertEquals(colorloop, lightBulb.isColorLoop());
    }

    @Test
    void testSetters(){
        //arrange
        String name = "name";
        boolean on = true;
        short hue = 500;
        byte saturation = 100;
        byte brightness = -100;
        boolean colorloop = false;

        LightBulb lightBulb = new LightBulb("UID", name, on, hue, saturation ,brightness, colorloop);

        String name2 = "name2";
        boolean on2 = false;
        short hue2 = 1000;
        byte saturation2 = 10;
        byte brightness2 = -10;
        boolean colorloop2 = true;

        //act
        lightBulb.setName(name2);
        lightBulb.setOn(on2);
        lightBulb.setHue(hue2);
        lightBulb.setSaturation(saturation2);
        lightBulb.setBrightness(brightness2);
        lightBulb.setColorLoop(colorloop2);


        assertEquals(name2 , lightBulb.getName());
        assertEquals(on2 , lightBulb.isOn());
        assertEquals(hue2, lightBulb.getHue());
        assertEquals(saturation2, lightBulb.getSaturation());
        assertEquals(brightness2, lightBulb.getBrightness());
        assertEquals(colorloop2, lightBulb.isColorLoop());
    }

}