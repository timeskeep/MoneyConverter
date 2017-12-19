package com.bad_coders.moneyconverter.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

import com.bad_coders.moneyconverter.Db.CurrencyDao;
import com.bad_coders.moneyconverter.Db.CurrencyDatabase;
import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.Ui.ConverterActivity;

/**
 * Created on 17.11.2017.
 */

public class ItemViewModel extends BaseObservable {
    private Currency mCurrency;
    private Context mContext;

    public ItemViewModel(Context mContext) {
        this.mContext = mContext;
    }

    public Currency getCurrency() {
        return mCurrency;
    }

    @Bindable
    public void setCurrency(Currency currency) {
        mCurrency = currency;
        notifyChange();
    }

    public void onItemClick(View view, Currency currency) {
        Intent intent = new Intent(view.getContext(), ConverterActivity.class);
        intent.putExtra(mContext.getString(R.string.info_extra_key), currency);
        view.getContext().startActivity(intent);
    }
}
