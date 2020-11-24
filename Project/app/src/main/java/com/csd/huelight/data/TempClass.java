package com.csd.huelight.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * een temp klasse waar wat lampen worden opgeslagen
 */
public class  TempClass {
    public static List<LightBulb> LightBulbs = new ArrayList<>();

    static {
        Random random = new Random();
        for (int count = 0; count < 25; count++) {
            LightBulbs.add(new LightBulb("UID"+random.nextInt(), "LightBulb " + count, random.nextBoolean(), (short)random.nextInt(), (byte) random.nextInt(), (byte)random.nextInt(), false));
        }
    }
}
