package com.bad_coders.moneyconverter.Ui;

import android.support.v4.app.Fragment;

import com.bad_coders.moneyconverter.Model.Currency;

public class ConverterActivity extends BaseSingleFragmentActivity {

    @Override
    public Fragment getFragment() {
        Currency currency = (Currency) getIntent().getSerializableExtra("info");
        return ConverterFragment.newInstance(currency);
    }
}
