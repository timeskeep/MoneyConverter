package com.bad_coders.moneyconverter.ViewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.bad_coders.moneyconverter.BR;
import com.bad_coders.moneyconverter.Model.Converter;
import com.bad_coders.moneyconverter.Model.Currency;

/**
 * Created on 20.11.2017.
 */

public class ConverterViewModel extends BaseObservable {
    public static final String TAG = ConverterViewModel.class.getSimpleName();
    private Converter mConverter;

    public ConverterViewModel(Currency currency) {
        mConverter = new Converter(currency.getRate(), "UAH", currency.getCurSymb());
    }

    @Bindable
    public Converter getConverter() {
        return mConverter;
    }

    public void setConverter(Converter converter) {
        mConverter = converter;
    }

    public void onTextChanged(CharSequence text) {
        if (text.length() != 0) {
            double amount = Double.valueOf(text.toString());
            mConverter.setAmount(amount);
        }
        notifyPropertyChanged(BR.converter);
    }

    public void onSwapFabClick() {
        mConverter.swapCurrencies();
        notifyPropertyChanged(BR.converter);
    }
}
