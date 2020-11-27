package com.csd.huelight.data;

import java.io.Serializable;
import java.util.Objects;

public class LightBulb implements Serializable {
    private final String UID;
    private String id;
    private String name;
    private boolean on;
    private int hue;
    private short saturation;
    private short brightness;
    private boolean colorLoop;

    public LightBulb(String UID,String id, String name, boolean on, short hue, byte saturation, byte brightness, boolean colorLoop) {
        this.UID = UID;
        this.id = id;
        this.name = name;
        this.on = on;
        this.hue = hue;
        this.saturation = saturation;
        this.brightness = brightness;
        this.colorLoop = colorLoop;
    }

    public String getUID() {
        return UID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getHue() {
        return hue;
    }

    public void setHue(int hue) {
        this.hue = hue;
    }

    public short getSaturation() {
        return saturation;
    }

    public void setSaturation(short saturation) {
        this.saturation = saturation;
    }

    public short getBrightness() {
        return brightness;
    }

    public void setBrightness(short brightness) {
        this.brightness = brightness;
    }

    public boolean isColorLoop() {
        return colorLoop;
    }

    public void setColorLoop(boolean colorLoop) {
        this.colorLoop = colorLoop;
    }

    public boolean completeEqual(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LightBulb lightBulb = (LightBulb) o;
        return on == lightBulb.on &&
                hue == lightBulb.hue &&
                saturation == lightBulb.saturation &&
                brightness == lightBulb.brightness &&
                colorLoop == lightBulb.colorLoop &&
                Objects.equals(UID, lightBulb.UID) &&
                Objects.equals(name, lightBulb.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LightBulb lightBulb = (LightBulb) o;
        return UID.equals(lightBulb.UID);
    }

    public void setSettings(LightBulb lightBulb){
        this.name = lightBulb.name;
        this.on = lightBulb.on;
        this.hue = lightBulb.hue;
        this.saturation = lightBulb.saturation;
        this.brightness = lightBulb.brightness;
        this.colorLoop = lightBulb.colorLoop;
    }
}
