package com.csd.huelight.ui.mainactivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.csd.huelight.R;
import com.csd.huelight.data.LightBulb;
import com.csd.huelight.ui.mainactivity.lightbulblist.LightBulbListFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.slider.Slider;

import java.util.List;

public class LightBulbFragment extends Fragment {

    private LightBulbViewModel lightBulbViewModel;


    public static LightBulbFragment newInstance() {
        return new LightBulbFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.light_bulb_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        lightBulbViewModel = ViewModelProviders.of(getActivity()).get(LightBulbViewModel.class);
        // TODO: Use the ViewModel

        LightBulb lightBulb = (LightBulb) getArguments().getSerializable("lightbulb");

        TextView UID = getActivity().findViewById(R.id.textViewUniqueID);
        UID.setText(lightBulb.getUID());

        TextView name = getActivity().findViewById(R.id.editTextName);
        name.setText(lightBulb.getName());

        Chip powerChip = getActivity().findViewById(R.id.chipOn);
        powerChip.setChecked(lightBulb.isOn());

        //TODO hue/brightness/saturation
        Slider sliderHue = getActivity().findViewById(R.id.sliderHue);
//        sliderHue.setValue(lightBulb.getHue());

        Slider sliderSaturation = getActivity().findViewById(R.id.sliderSaturation);

        Slider sliderBrightness = getActivity().findViewById(R.id.sliderBrightness);

        Chip colorLoopChip = getActivity().findViewById(R.id.chipColorloop);
        colorLoopChip.setChecked(lightBulb.isColorLoop());

        Button click = getActivity().findViewById(R.id.buttonSaveChanges);
        click.setOnClickListener(view -> {
            for (LightBulb l : lightBulbViewModel.getLightBulbs().getValue()) {
                if(l == lightBulb){
                    l.setName(name.getText().toString());
                    l.setOn(powerChip.isChecked());
                    l.setColorLoop(colorLoopChip.isChecked());
                }
            }
        });

    }

}