package com.first.nishant.greapptracker;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by Nishant on 8/22/2015.
 */
public class SettingsActivityFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_prof1_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_prof2_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_prof3_key)));
    }

    private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);
        onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String s= newValue.toString();
        preference.setSummary(s);
        return true;
    }
}
