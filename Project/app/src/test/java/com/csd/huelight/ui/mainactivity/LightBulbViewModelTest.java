package com.csd.huelight.ui.mainactivity;

import com.csd.huelight.data.ObservableLightBulbApiManager;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import okhttp3.mockwebserver.MockWebServer;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
class LightBulbViewModelTest {
    final MockWebServer mockWebServer = new MockWebServer();

    @Mock
    private ObservableLightBulbApiManager apiManager;

    @InjectMocks
    private LightBulbViewModel lightBulbViewModel = new LightBulbViewModel();

    @Before
    void init() {
        lightBulbViewModel.init(apiManager);
    }

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer.start();
        MockitoAnnotations.openMocks(this);
        lightBulbViewModel.init(apiManager);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void initTest() {
        verify(apiManager, times(1)).retrieveLightBulbs();

        lightBulbViewModel.init(apiManager);

        verify(apiManager, times(1)).retrieveLightBulbs();
    }

    @Test
    void retrieveLightBulbsTest() {
        verify(apiManager, times(1)).retrieveLightBulbs();

        lightBulbViewModel.retrieveLightBulbs();

        verify(apiManager, times(2)).retrieveLightBulbs();

    }

    @Test
    void setLightBulbStateTest() {
        lightBulbViewModel.setLightBulbState(null);

        verify(apiManager, times(1)).setLightBulbState(null);
    }

    @Test
    void setLightBulbNameTest() {
        lightBulbViewModel.setLightBulbName(null);

        verify(apiManager, times(1)).setLightBulbName(null);
    }

    @Test
    void getLightBulbsNotNullTest(){
        assertNotNull(lightBulbViewModel.getLightBulbs());
        assertNotNull(lightBulbViewModel.getLightBulbs().getValue());
    }

    @Test
    void getCallsNotNullTest(){
        assertNotNull(lightBulbViewModel.getCalls());
        assertNotNull(lightBulbViewModel.getCalls().getValue());
    }
    @Test
    void getExceptionMessageNotNullTest(){
        assertNotNull(lightBulbViewModel.getExceptionMessage());
        assertNotNull(lightBulbViewModel.getExceptionMessage().getValue());
    }


}