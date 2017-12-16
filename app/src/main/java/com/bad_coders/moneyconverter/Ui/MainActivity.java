package com.bad_coders.moneyconverter.Ui;

import android.support.v4.app.Fragment;

public class MainActivity extends DrawerActivity {

    @Override
    public Fragment getFragment() {
        return ExchangeListFragment.newInstance();
    }
}
