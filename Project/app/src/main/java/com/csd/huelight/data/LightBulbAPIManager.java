package com.csd.huelight.data;

import java.util.List;

public interface LightBulbAPIManager {
    List<LightBulb> getLightBulbs();

    void setLightBulbs(List<LightBulb> lightBulbs);

    Exception getException();

    byte getCalls();

    void retrieveLightBulbs();

    void setLightBulbState(LightBulb lightBulb);

    void setLightBulbName(LightBulb lightBulb);

}
