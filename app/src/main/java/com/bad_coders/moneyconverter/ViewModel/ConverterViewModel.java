package com.bad_coders.moneyconverter.ViewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bad_coders.moneyconverter.BR;
import com.bad_coders.moneyconverter.Db.CurrencyDao;
import com.bad_coders.moneyconverter.Db.CurrencyDatabase;
import com.bad_coders.moneyconverter.Model.Converter;
import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.R;
import com.squareup.picasso.Picasso;

/**
 * Created on 20.11.2017.
 */

public class ConverterViewModel extends BaseObservable {
    public static final String TAG = ConverterViewModel.class.getSimpleName();
    private String mPurchaseName;
    private String mSaleCurName;
    private String mSaleSymb;
    private String mPurchaseSymb;
    private Context mContext;
    private Converter mConverter;
    private Uri mGraphUri;
    private static Uri baseGraphUri;

    static {
        baseGraphUri = Uri.parse("https://www.google.com/finance/chart").buildUpon()
                .appendQueryParameter("tkr", "1")
                .appendQueryParameter("p", "5Y")
                .appendQueryParameter("chs", "1000x1000").build();
    }

    public ConverterViewModel(Currency currency, Context context) {
        mPurchaseSymb = currency.getCurSymb();
        mSaleSymb = currency.getBaseCurSymb();
        mConverter = new Converter(currency.getRate(), mSaleSymb, mPurchaseSymb);
        mContext = context;
        CurrencyDao currencyDatabase = CurrencyDatabase.newInstance(context)
                .getCurrencyDao();
        mSaleCurName = currencyDatabase.getCurName(mPurchaseSymb);
        mPurchaseName = currencyDatabase.getCurName(mSaleSymb);
        mGraphUri = baseGraphUri.buildUpon()
                .appendQueryParameter("q", "CURRENCY:".concat(mPurchaseSymb.concat(mSaleSymb)))
                .build();
    }

    @Bindable
    public Converter getConverter() {
        return mConverter;
    }

    @Bindable
    public String getPurchaseName() {
        return mPurchaseName;
    }

    @Bindable
    public String getSaleCurName() {
        return mSaleCurName;
    }

    @Bindable
    public Uri getGraphUri() {
        return mGraphUri;
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
        String temp = mSaleCurName;
        mSaleCurName = mPurchaseName;
        mPurchaseName = temp;
        mConverter.swapCurrencies();
        String tempSymb = mPurchaseSymb;
        mPurchaseSymb = mSaleSymb;
        mSaleSymb = tempSymb;
        mGraphUri = baseGraphUri
                .buildUpon()
                .appendQueryParameter("q", "CURRENCY:".concat(mPurchaseSymb.concat(mSaleSymb)))
                .build();
        notifyPropertyChanged(BR.converter);
        notifyPropertyChanged(BR.purchaseName);
        notifyPropertyChanged(BR.saleCurName);
        notifyPropertyChanged(BR.graphUri);
    }

    @BindingAdapter({"bind:loadGraph"})
    public static void loadGraph(ImageView view, Uri graphUrl) {
        Picasso.with(view.getContext()).load(graphUrl).into(view);
    }
}
