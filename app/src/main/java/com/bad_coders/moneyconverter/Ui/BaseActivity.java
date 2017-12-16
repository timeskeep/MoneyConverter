package com.bad_coders.moneyconverter.Ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bad_coders.moneyconverter.R;

/**
 * Created on 17.11.2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public abstract Fragment getFragment();
    public abstract void setLayout();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = getFragment();
            String tag = fragment.getClass().getSimpleName();
            fragmentManager.beginTransaction().add(R.id.container, fragment, tag).commit();
        }
    }
}
