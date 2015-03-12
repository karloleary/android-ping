package com.karloleary.android.projectping;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by karl on 12/03/15.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
