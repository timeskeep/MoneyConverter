package com.bad_coders.moneyconverter.ViewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import android.widget.Toast;

import com.bad_coders.moneyconverter.BR;
import com.bad_coders.moneyconverter.Model.Converter;
import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.R;

/**
 * Created on 20.11.2017.
 */

public class ConverterViewModel extends BaseObservable {
    private Context mContext;
    public static final String TAG = ConverterViewModel.class.getSimpleName();
    private Converter mConverter;

    public ConverterViewModel(Currency currency, Context context) {
        mConverter = new Converter(currency.getRate(), "UAH", currency.getCurSymb());
        mContext = context;
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
            try {
                double amount = Double.valueOf(text.toString());
                mConverter.setAmount(amount);
            } catch (NumberFormatException e) {
                Log.e(TAG, e.getMessage());
                Toast.makeText(mContext,
                        R.string.parse_error_message,
                        Toast.LENGTH_LONG).show();
            }
        }
        notifyPropertyChanged(BR.converter);
    }

    public void onSwapFabClick() {
        mConverter.swapCurrencies();
        notifyPropertyChanged(BR.converter);
    }
}
