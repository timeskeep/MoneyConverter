package com.bad_coders.moneyconverter.Ui;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.Toolbar;

import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.databinding.ActivityBaseBinding;

/**
 * Created on 15.12.2017.
 */

public abstract class SingleFragmentActivity extends BaseActivity {
    private ActivityBaseBinding mBinding;

    @Override
    public void setLayout() {
        mBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_base
        );
        Toolbar toolbar = mBinding.toolbar;
        setSupportActionBar(toolbar);
    }
}
