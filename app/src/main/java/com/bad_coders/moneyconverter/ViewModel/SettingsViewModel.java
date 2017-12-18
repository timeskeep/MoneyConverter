package com.bad_coders.moneyconverter.ViewModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.bad_coders.moneyconverter.Ui.MainActivity;
import com.bad_coders.moneyconverter.Ui.SettingsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.List;
import java.util.Objects;

/**
 * Created on 16.12.2017.
 */

public class SettingsViewModel implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = SettingsViewModel.class.getSimpleName();
    private PreferenceScreen mPreferenceScreen;
    private CurrencyDatabase mDatabase;

    public SettingsViewModel(PreferenceScreen preferenceScreen) {
        mPreferenceScreen = preferenceScreen;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference = mPreferenceScreen.findPreference(s);
        if (preference != null) {
            if (!(preference instanceof CheckBoxPreference)) {
                setPreferenceSummary(preference, sharedPreferences.getString(s, ""));
            }
            if (Objects.equals(preference.getKey(), "currency_base")) {
                double rate = mDatabase.getCurrencyDao().getRate(
                        sharedPreferences.getString(preference.getKey(), "")
                );
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("rate_base", (float) rate);
                editor.apply();
            }
        }
        Intent i = mPreferenceScreen.getContext().getPackageManager().
                getLaunchIntentForPackage(mPreferenceScreen.getContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mPreferenceScreen.getContext().startActivity(i);
    }

    private void setPreferenceSummary(Preference preference, Object value) {
        String stringValue = value.toString();
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }

    public void setupCurrencyList() {
        mDatabase = CurrencyDatabase.newInstance(mPreferenceScreen.getContext());
        List<String> symbols = mDatabase.getCurrencyDao().getCurSymbols();
        ListPreference listPreference = (ListPreference) mPreferenceScreen.findPreference("currency_base");
        listPreference.setEntries(symbols.toArray(new CharSequence[symbols.size()]));
        listPreference.setEntryValues(symbols.toArray(new CharSequence[symbols.size()]));
    }
}
