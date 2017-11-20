package com.bad_coders.moneyconverter.ViewModel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.Ui.ConverterActivity;

/**
 * Created on 17.11.2017.
 */

public class RateListItemViewModel extends BaseObservable {
    private Currency mCurrency;

    public Currency getCurrency() {
        return mCurrency;
    }

    @Bindable
    public void setCurrency(Currency currency) {
        mCurrency = currency;
        notifyChange();
    }

    public void onItemClick(View view,  Currency currency) {
        Intent intent = new Intent(view.getContext(), ConverterActivity.class);
        intent.putExtra("info", currency);
        view.getContext().startActivity(intent);
    }
}
