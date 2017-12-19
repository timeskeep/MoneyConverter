package com.bad_coders.moneyconverter.Ui;

import android.support.v4.app.Fragment;

import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.R;

public class ConverterActivity extends SingleFragmentActivity {
    @Override
    public Fragment getFragment() {
        getSupportActionBar().setHomeButtonEnabled(true);
        Currency currency = (Currency) getIntent().getSerializableExtra(getString(R.string.info_extra_key));
        return ConverterFragment.newInstance(currency);
    }
}
