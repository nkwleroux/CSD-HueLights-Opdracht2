package com.csd.huelight.ui.mainactivity.settings;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.csd.huelight.R;

public class SettingsFragment extends PreferenceFragmentCompat {

//    APIManager apiManager;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
//        apiManager = APIManager.getInstance();

        Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(preference.getKey().equals("IP")){

                }


                return false;
            }
        };

    }
}