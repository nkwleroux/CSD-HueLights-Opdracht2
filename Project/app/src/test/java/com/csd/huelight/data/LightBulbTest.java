package com.csd.huelight.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LightBulbTest {

    @Test
    void gettersTest() {
        //arrange
        String UID = "UID";
        String id = "id";
        String name = "name";
        boolean on = true;
        short hue = 500;
        byte saturation = 100;
        byte brightness = -100;
        boolean colorloop = false;

        //act
        LightBulb lightBulb = new LightBulb(UID, id, name, on, hue, saturation, brightness, colorloop);

        //assert
        assertEquals(UID, lightBulb.getUID());
        assertEquals(id, lightBulb.getId());
        assertEquals(name, lightBulb.getName());
        assertEquals(on, lightBulb.isOn());
        assertEquals(hue, lightBulb.getHue());
        assertEquals(saturation, lightBulb.getSaturation());
        assertEquals(brightness, lightBulb.getBrightness());
        assertEquals(colorloop, lightBulb.isColorLoop());
    }

    @Test
    void settersTest() {
        //arrange
        String id = "id";
        String name = "name";
        boolean on = true;
        short hue = 500;
        byte saturation = 100;
        byte brightness = -100;
        boolean colorloop = false;

        LightBulb lightBulb = new LightBulb("UID", id, name, on, hue, saturation, brightness, colorloop);

        String id2 = "id2";
        String name2 = "name2";
        boolean on2 = false;
        short hue2 = 1000;
        byte saturation2 = 10;
        byte brightness2 = -10;
        boolean colorloop2 = true;

        //act
        lightBulb.setId(id2);
        lightBulb.setName(name2);
        lightBulb.setOn(on2);
        lightBulb.setHue(hue2);
        lightBulb.setSaturation(saturation2);
        lightBulb.setBrightness(brightness2);
        lightBulb.setColorLoop(colorloop2);

        //assert
        assertEquals(id2, lightBulb.getId());
        assertEquals(name2, lightBulb.getName());
        assertEquals(on2, lightBulb.isOn());
        assertEquals(hue2, lightBulb.getHue());
        assertEquals(saturation2, lightBulb.getSaturation());
        assertEquals(brightness2, lightBulb.getBrightness());
        assertEquals(colorloop2, lightBulb.isColorLoop());
    }

    @Test
    void CompleteEqualsTest(){
        //arrange
        String UID = "UID";
        String id = "id";
        String name = "name";
        boolean on = true;
        short hue = 500;
        byte saturation = 100;
        byte brightness = -100;
        boolean colorloop = false;

        //act
        LightBulb lightBulb1 = new LightBulb(UID, id, name, on, hue, saturation, brightness, colorloop);
        LightBulb lightBulb2 = new LightBulb(UID, id, name, on, hue, saturation, brightness, colorloop);
        LightBulb lightBulb3 = new LightBulb(UID, "not id", name, on, hue, saturation, brightness, colorloop);

        //assert
        assertTrue(lightBulb1.completeEqual(lightBulb2));

        assertFalse(lightBulb1.completeEqual(lightBulb3));
    }

    @Test
    void EqualsTest(){
        //arrange
        String UID = "UID";
        String id = "id";
        String name = "name";
        boolean on = true;
        short hue = 500;
        byte saturation = 100;
        byte brightness = -100;
        boolean colorloop = false;

        //act
        LightBulb lightBulb1 = new LightBulb(UID, id, name, on, hue, saturation, brightness, colorloop);
        LightBulb lightBulb2 = new LightBulb(UID, id, name, on, hue, saturation, brightness, colorloop);
        LightBulb lightBulb3 = new LightBulb(UID, "not id", name, on, hue, saturation, brightness, colorloop);
        LightBulb lightBulb4 = new LightBulb("not UID", id, name, on, hue, saturation, brightness, colorloop);

        //assert
        assertTrue(lightBulb1.equals(lightBulb2));
        assertTrue(lightBulb1.equals(lightBulb3));
        assertFalse(lightBulb1.equals(lightBulb4));
    }

    @Test
    void setSettingsTest(){
        //arrange
        String id = "id";
        String name = "name";
        boolean on = true;
        short hue = 500;
        byte saturation = 100;
        byte brightness = -100;
        boolean colorloop = false;

        LightBulb lightBulb = new LightBulb("UID", id, name, on, hue, saturation, brightness, colorloop);

        String id2 = "id2";
        String name2 = "name2";
        boolean on2 = false;
        short hue2 = 1000;
        byte saturation2 = 10;
        byte brightness2 = -10;
        boolean colorloop2 = true;

        LightBulb lightBulb2 = new LightBulb("UID", id2, name2, on2, hue2, saturation2, brightness2, colorloop2);

        //act
        lightBulb.setSettings(lightBulb2);

        //assert
        assertEquals(id2, lightBulb.getId());
        assertEquals(name2, lightBulb.getName());
        assertEquals(on2, lightBulb.isOn());
        assertEquals(hue2, lightBulb.getHue());
        assertEquals(saturation2, lightBulb.getSaturation());
        assertEquals(brightness2, lightBulb.getBrightness());
        assertEquals(colorloop2, lightBulb.isColorLoop());

    }

}