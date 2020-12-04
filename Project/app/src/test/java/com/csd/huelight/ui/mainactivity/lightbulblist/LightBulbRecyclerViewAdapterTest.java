package com.csd.huelight.ui.mainactivity.lightbulblist;

import com.csd.huelight.data.LightBulb;
import com.csd.huelight.ui.mainactivity.LightBulbViewModel;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LightBulbRecyclerViewAdapterTest {

    @Mock
    private LightBulbClickListener clickListener;

    @Mock
    private LightBulbViewModel viewModel;

    private List<LightBulb> lightBulbList = new ArrayList<>();

    @InjectMocks
    private final LightBulbRecyclerViewAdapter lightBulbRecyclerViewAdapter = new LightBulbRecyclerViewAdapter(lightBulbList, clickListener, viewModel);

    @Test
    void updateTest() {
        LightBulb lightBulb = new LightBulb("UID", "id", "name", true, (short) 500, (byte) 100, (byte) -100, false);

        List<LightBulb> lightBulbList2 = new ArrayList<LightBulb>();
        lightBulbList2.add(lightBulb);

        assertEquals(lightBulbRecyclerViewAdapter.getItemCount(), 0);

        try {
            lightBulbRecyclerViewAdapter.updateLightBulbs(lightBulbList2);
        }catch (Exception e){
            //catch notifydataset werkt niet omdat het niet in een android omgeving zit
        }

        assertEquals(lightBulbRecyclerViewAdapter.getItemCount(), 1);
    }

}