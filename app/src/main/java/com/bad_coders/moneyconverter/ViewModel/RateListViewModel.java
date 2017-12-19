package com.bad_coders.moneyconverter.ViewModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.Toast;

import com.bad_coders.moneyconverter.Adapter.RateAdapter;
import com.bad_coders.moneyconverter.BR;
import com.bad_coders.moneyconverter.Db.CurrencyDatabase;
import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.Model.RateFetcher;
import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.Ui.DrawerActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Response;

/**
 * Created on 17.11.2017.
 */

public class RateListViewModel extends BaseObservable
        implements RateFetcher.OnRateFetched {
    private DrawerActivity mActivity;
    private RateFetcher mRateFetcher;
    private boolean isLoadFinished;
    private boolean isLoadSuccess;
    private RateAdapter mAdapter;
    private CurrencyDatabase mDatabase;

    public RateListViewModel(RateAdapter adapter, DrawerActivity activity) {
        mAdapter = adapter;
        mActivity = activity;
        mDatabase = CurrencyDatabase.newInstance(activity.getBaseContext());
        mRateFetcher = new RateFetcher(this);
        activity.getSupportActionBar().setTitle(R.string.exchange_rate_list_label);
        mRateFetcher.loadRateList();
    }

    private void notifyLayout() {
        notifyPropertyChanged(BR.loadFinished);
        notifyPropertyChanged(BR.loadSuccess);
    }

    @Bindable
    public boolean isLoadFinished() {
        return isLoadFinished;
    }

    @Bindable
    public boolean isLoadSuccess() {
        return isLoadSuccess;
    }

    public void onTryAgainButtonClick() {
        isLoadFinished = false;
        isLoadSuccess = false;
        notifyLayout();
        mRateFetcher.loadRateList();
    }

    @Override
    public void onResponse(Response<List<Currency>> response) {
        List<Currency> rateList = response.body();
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        mActivity.getSupportActionBar().setSubtitle(mActivity
                .getApplicationContext().getString(R.string.last_update, df.format(new Date())));
        rateList.add(new Currency("Українська гривня", 1, "UAH"));
        mDatabase.getCurrencyDao().deleteAll();
        mDatabase.getCurrencyDao().insertAll(rateList);
        isLoadFinished = true;
        isLoadSuccess = true;
        notifyLayout();
        mAdapter.swapList(rateList);
    }

    @Override
    public void onFailure(Throwable t) {
        List<Currency> rateList = mDatabase.getCurrencyDao().getList();
        isLoadFinished = true;
        if (rateList.size() != 0) {
            mAdapter.swapList(rateList);
            isLoadSuccess = true;
        } else {
            Toast.makeText(mActivity.getBaseContext(),
                    mActivity.getString(R.string.connection_error, t.getMessage()),
                    Toast.LENGTH_LONG).show();
            isLoadSuccess = false;
        }
        notifyLayout();
    }
}
