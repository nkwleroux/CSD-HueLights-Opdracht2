package com.csd.huelight.ui.mainactivity.settings;

import android.os.Bundle;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.csd.huelight.R;
import com.csd.huelight.data.APIManager;

public class SettingsFragment extends PreferenceFragmentCompat {

    static APIManager apiManager;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        apiManager = APIManager.getInstance();

        Preference ip = findPreference("IP");
        ip.setSummary(apiManager.getIp());
        ip.setOnPreferenceChangeListener(listener);


        Preference PORT_NUM = findPreference("PORT_NUM");
        PORT_NUM.setSummary(apiManager.getPort());
        PORT_NUM.setOnPreferenceChangeListener(listener);


        Preference DEV_NAME = findPreference("DEV_NAME");
        DEV_NAME.setSummary(apiManager.getUsername());
        DEV_NAME.setOnPreferenceChangeListener(listener);

    }

    private static Preference.OnPreferenceChangeListener listener = (preference, newValue) -> {
        if (preference instanceof EditTextPreference) {
            String key = preference.getKey();
            preference.setPersistent(true);
            switch (key) {
                case "IP":
                    preference.setSummary((CharSequence) newValue);
                    apiManager.setIp((String) newValue);
                    break;
                case "PORT_NUM":
                    preference.setSummary((CharSequence) newValue);
                    String text = (String) newValue;
                    int value = Integer.valueOf(text);
                    apiManager.setPort(value);

                    break;
                case "DEV_NAME":
                    preference.setSummary((CharSequence) newValue);
                    apiManager.setUsername((String) newValue);
                    break;
                default:
                    break;

            }
        }

        return false;
    };
}