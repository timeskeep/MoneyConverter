package com.bad_coders.moneyconverter.ViewModel;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceCategory;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.Ui.DrawerActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by taras on 12/18/17.
 */

public class UserSettingViewModel implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = UserSettingViewModel.class.getSimpleName();
    private SharedPreferences mSharedPreferences;
    private PreferenceScreen mPreferenceScreen;
    private FirebaseUser mFirebaseUser;
    private DrawerActivity mActivity;
    private UserSettingViewModel.OperationCompleteListener mOperationCompleteListener;

    public UserSettingViewModel(DrawerActivity activity, PreferenceScreen preferenceScreen) {
        mActivity = activity;
        mPreferenceScreen = preferenceScreen;
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mOperationCompleteListener = new OperationCompleteListener();
        mSharedPreferences = mPreferenceScreen.getSharedPreferences();
        updateSummaries();
    }

    private void updateSummaries() {
        for (int i = 0; i < mPreferenceScreen.getPreferenceCount(); i++) {
            Preference p = mPreferenceScreen.getPreference(i);
            if (!(p instanceof PreferenceCategory))
                setPreferenceSummary(p, mSharedPreferences.getString(p.getKey(), ""));
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        switch (s) {
            case "email_address":
                startOperation(s, R.string.change_email_mes);
                break;
            case "password":
                startOperation(s, R.string.change_password_mes);
                break;
            case "name":
                startOperation(s, R.string.change_name_mes);
                break;
        }
    }

    private void startOperation(String s, int operationCode) {
        mOperationCompleteListener.setOperationName(operationCode);
        mOperationCompleteListener.setOperationKey(s);
        Task<Void> mTask;
        switch (operationCode) {
            case R.string.change_email_mes:
                mTask = mFirebaseUser.updateEmail(mSharedPreferences.getString(s, ""));
                break;
            case R.string.change_password_mes:
                mTask = mFirebaseUser.updatePassword(mSharedPreferences.getString(s, ""));
                break;
            case R.string.change_name_mes:
                mTask = mFirebaseUser.updateProfile(new UserProfileChangeRequest.Builder()
                        .setDisplayName(mSharedPreferences.getString(s, "")).build());
                break;
            default:
                Log.e(TAG, "Unknown operation" + operationCode);
                mTask = null;
        }
        if (mTask != null)
            mTask.addOnCompleteListener(mOperationCompleteListener);
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

    private class OperationCompleteListener implements OnCompleteListener<Void> {
        private int operationName;
        private String operationKey;

        public void setOperationName(int operationName) {
            this.operationName = operationName;
        }

        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (!task.isSuccessful()) {
                try {
                    throw task.getException();
                } catch (FirebaseAuthInvalidCredentialsException e) {
                    Toast.makeText(mActivity.getApplicationContext(),
                            e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (FirebaseAuthRecentLoginRequiredException e) {
                    Toast.makeText(mActivity.getApplicationContext(),
                            mActivity.getString(R.string.recently_login_error), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage());
                }
                return;
            }

            Toast.makeText(mPreferenceScreen.getContext(),
                    mPreferenceScreen.getContext().getString(R.string.operation_success_toast,
                            mPreferenceScreen.getContext().getString(operationName)),
                    Toast.LENGTH_LONG).show();

            View headerView = mActivity.findViewById(R.id.header_view);
            switch (operationName) {
                case R.string.change_email_mes:
                    TextView emailView = headerView.findViewById(R.id.profile_email);
                    emailView.setText(mSharedPreferences.getString(operationKey, ""));
                    break;
                case R.string.change_name_mes:
                    TextView nameView = headerView.findViewById(R.id.profile_name);
                    nameView.setText(mSharedPreferences.getString(operationKey, ""));
                    break;
            }
            updateSummaries();
        }

        public void setOperationKey(String operationKey) {
            this.operationKey = operationKey;
        }
    }
}
