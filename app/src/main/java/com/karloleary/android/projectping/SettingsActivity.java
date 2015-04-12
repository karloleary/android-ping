package com.karloleary.android.projectping;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import java.util.List;

/**
 * Created by karl on 12/03/15.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.preferences_app, target);
    }
}
