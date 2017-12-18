package com.bad_coders.moneyconverter.Ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.ViewModel.UserSettingViewModel;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;

/**
 * Created by taras on 12/18/17.
 */

public class UserSettingsFragment extends PreferenceFragmentCompat {
    private UserSettingViewModel mSettingsViewModel;
    public static UserSettingsFragment newInstance() {
        UserSettingsFragment fragment = new UserSettingsFragment();
        return fragment;
    }

    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences_user, rootKey);
        mSettingsViewModel = new UserSettingViewModel((DrawerActivity) getActivity(), getPreferenceScreen());
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
