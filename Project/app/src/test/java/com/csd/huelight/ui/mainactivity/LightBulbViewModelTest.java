package com.csd.huelight.ui.mainactivity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LightBulbViewModelTest {

    @Test
    void notNullAfterInitTest() {

        LightBulbViewModel lightBulbViewModel = new LightBulbViewModel();

        lightBulbViewModel.init();

    }
}