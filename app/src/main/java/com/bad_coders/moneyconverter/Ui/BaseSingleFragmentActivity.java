package com.bad_coders.moneyconverter.Ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.bad_coders.moneyconverter.R;

/**
 * Created on 17.11.2017.
 */

public abstract class BaseSingleFragmentActivity extends AppCompatActivity {
    public abstract Fragment getFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = getFragment();
            fragmentManager.beginTransaction().add(R.id.container, fragment).commit();
        }
    }
}
