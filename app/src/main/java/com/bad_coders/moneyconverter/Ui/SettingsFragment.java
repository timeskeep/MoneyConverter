package com.bad_coders.moneyconverter.Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.ViewModel.SettingsViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created on 16.12.2017.
 */

public class SettingsFragment extends PreferenceFragmentCompat {
    private SettingsViewModel mSettingsViewModel;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_app, rootKey);
        mSettingsViewModel = new SettingsViewModel(getPreferenceScreen());
        mSettingsViewModel.setupCurrencyList();
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(mSettingsViewModel);
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(mSettingsViewModel);
    }
}
