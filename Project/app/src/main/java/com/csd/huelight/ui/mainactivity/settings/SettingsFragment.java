package com.csd.huelight.ui.mainactivity.settings;

import android.os.Bundle;

import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.csd.huelight.R;
import com.csd.huelight.data.APIManager;

public class SettingsFragment extends PreferenceFragmentCompat {

    static APIManager apiManager;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        apiManager = APIManager.getInstance();

        EditTextPreference ip = findPreference("IP");
        ip.setSummary(apiManager.getIp());
        ip.setText(apiManager.getIp());
        ip.setOnPreferenceChangeListener(listener);

        EditTextPreference PORT_NUM = findPreference("PORT_NUM");
        PORT_NUM.setSummary(String.valueOf(apiManager.getPort()));
        PORT_NUM.setText(String.valueOf(apiManager.getPort()));
        PORT_NUM.setOnPreferenceChangeListener(listener);

        EditTextPreference DEV_NAME = findPreference("DEV_NAME");
        DEV_NAME.setSummary(apiManager.getUsername());
        DEV_NAME.setText(apiManager.getUsername());
        DEV_NAME.setOnPreferenceChangeListener(listener);

    }

    private static Preference.OnPreferenceChangeListener listener = (preference, newValue) -> {
        if (preference instanceof EditTextPreference) {
            String key = preference.getKey();
            preference.setPersistent(true);
            switch (key) {
                case "IP":
                    apiManager.setIp((String) newValue);
                    preference.setSummary(apiManager.getIp());
                    ((EditTextPreference) preference).setText(apiManager.getIp());
                    break;
                case "PORT_NUM":
                    String text = (String) newValue;
                    int value = Integer.valueOf(text);
                    apiManager.setPort(value);
                    preference.setSummary(String.valueOf(apiManager.getPort()));
                    ((EditTextPreference) preference).setText(String.valueOf(apiManager.getPort()));
                    break;
                case "DEV_NAME":
                    apiManager.setUsername((String) newValue);
                    preference.setSummary(apiManager.getUsername());
                    ((EditTextPreference) preference).setText(apiManager.getUsername());
                    break;
                default:
                    break;

            }
        }
        return false;
    };
}