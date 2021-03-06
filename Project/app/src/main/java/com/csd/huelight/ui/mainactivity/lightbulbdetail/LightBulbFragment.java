package com.csd.huelight.ui.mainactivity.lightbulbdetail;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.csd.huelight.R;
import com.csd.huelight.data.LightBulb;
import com.csd.huelight.ui.mainactivity.LightBulbViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.slider.Slider;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class LightBulbFragment extends Fragment {

    private static final String LOGTAG = LightBulbFragment.class.getName();

    private LightBulbViewModel lightBulbViewModel;
    private LightBulb lightBulb;
    private ImageView imageView;
    private EditText name;

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

        ProgressBar progressBar = getActivity().findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        this.lightBulbViewModel = ViewModelProviders.of(getActivity()).get(LightBulbViewModel.class);

        int position = getArguments().getInt("lightbulb");
        lightBulb = lightBulbViewModel.getLightBulbs().getValue().get(position);

        TextView UID = getActivity().findViewById(R.id.textViewUniqueID);
        UID.setText(lightBulb.getUID());

        name = getActivity().findViewById(R.id.editTextName);
        name.setText(lightBulb.getName());
        name.setOnFocusChangeListener((v, hasFocus) -> {
//            Log.d(LOGTAG, "focuschanged " + hasFocus);
            if (!hasFocus) {
                lightBulb.setName(name.getText().toString());
                lightBulbViewModel.setLightBulbName(lightBulb);
                closeKeyboard();
            }
        });
        name.setFocusableInTouchMode(true);

        Chip chipPower = getActivity().findViewById(R.id.chipOn);

        SetChipState("chipPower", chipPower, lightBulb.isOn());
        chipPower.setOnCheckedChangeListener((compoundButton, isChecked) ->
        {
            SetChipState("chipPower", chipPower, isChecked);
            lightBulbViewModel.setLightBulbState(lightBulb);
            SetImageViewColor();
            closeKeyboard();
        });

        Slider sliderHue = getActivity().findViewById(R.id.sliderHue);
        sliderHue.setValue(lightBulb.getHue());
        sliderHue.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                lightBulb.setHue((int) Math.abs(slider.getValue()));
                lightBulbViewModel.setLightBulbState(lightBulb);
                SetImageViewColor();
                closeKeyboard();
            }
        });

        Slider sliderSaturation = getActivity().findViewById(R.id.sliderSaturation);
        sliderSaturation.setValue(lightBulb.getSaturation());
        sliderSaturation.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                int value = (int) Math.abs(slider.getValue());
                lightBulb.setSaturation((short) value);
                lightBulbViewModel.setLightBulbState(lightBulb);
                SetImageViewColor();
                closeKeyboard();
            }
        });

        Slider sliderBrightness = getActivity().findViewById(R.id.sliderBrightness);
        sliderBrightness.setValue(lightBulb.getBrightness());
        sliderBrightness.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                int value = (int) Math.abs(slider.getValue());
                lightBulb.setBrightness((short) value);
                lightBulbViewModel.setLightBulbState(lightBulb);
                SetImageViewColor();
                closeKeyboard();
            }
        });

        Chip chipColorLoop = getActivity().findViewById(R.id.chipColorloop);

        SetChipState("chipColorLoop", chipColorLoop, lightBulb.isColorLoop());
        chipColorLoop.setOnCheckedChangeListener((compoundButton, isChecked) ->

        {
            SetChipState("chipColorLoop", chipColorLoop, isChecked);
            lightBulbViewModel.setLightBulbState(lightBulb);
            SetImageViewColor();
            closeKeyboard();
        });

        imageView = getActivity().findViewById(R.id.detailImageView);

        Picasso.get()
                .load(R.drawable.lightbulb)
                .placeholder(R.drawable.ic_baseline_sync_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                       SetImageViewColor();
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        name.clearFocus();
    }

    private void SetImageViewColor(){
        imageView.setColorFilter(lightBulb.isOn() ? Color.HSVToColor(
                new float[]{
                        lightBulb.getHue() / (float) Short.MAX_VALUE * 180f,
                        lightBulb.getSaturation() / 256f,
                        lightBulb.getBrightness() / 256f}) : Color.WHITE,
                PorterDuff.Mode.MULTIPLY);
    }

    private void SetChipState(String chipName, Chip chip, boolean state) {
        chip.setChecked(state);
        switch (chipName) {
            case "chipPower":
                lightBulb.setOn(state);
                if (state) {
                    chip.setText(getString(R.string.power_on));
                } else {
                    chip.setText(getString(R.string.power_off));
                }
                break;
            case "chipColorLoop":
                lightBulb.setColorLoop(state);
                if (state) {
                    chip.setText(getString(R.string.rainbow));
                } else {
                    chip.setText(getString(R.string.none));
                }
                break;
        }
    }

    private void closeKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null){
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            view.clearFocus();
        }
    }

}