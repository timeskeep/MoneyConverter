package com.bad_coders.moneyconverter.ViewModel;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bad_coders.moneyconverter.Db.CurrencyDatabase;
import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.Ui.DrawerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;

/**
 * Created on 16.12.2017.
 */

public class SettingsViewModel implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = SettingsViewModel.class.getSimpleName();
    private PreferenceScreen mPreferenceScreen;
    private DrawerActivity mActivity;

    public SettingsViewModel(PreferenceScreen preferenceScreen, DrawerActivity activity) {
        mPreferenceScreen = preferenceScreen;
        mActivity = activity;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference = mPreferenceScreen.findPreference(s);
        if (preference != null) {
            if (!(preference instanceof CheckBoxPreference)) {
                setPreferenceSummary(preference, sharedPreferences.getString(s, ""));
            }
        }

    }

    private void setPreferenceSummary(Preference preference, Object value) {
        String stringValue = value.toString();
        String key = preference.getKey();
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        } else {
            if (!key.equals("password"))
                preference.setSummary(stringValue);
        }
    }

    public void setupCurrencyList() {
        CurrencyDatabase database = CurrencyDatabase.newInstance(mPreferenceScreen.getContext());
        List<String> symbols = database.getCurrencyDao().getCurSymbols();
        ListPreference listPreference = (ListPreference) mPreferenceScreen.findPreference("currency_base");
        listPreference.setEntries(symbols.toArray(new CharSequence[symbols.size()]));
        listPreference.setEntryValues(symbols.toArray(new CharSequence[symbols.size()]));
    }
}
