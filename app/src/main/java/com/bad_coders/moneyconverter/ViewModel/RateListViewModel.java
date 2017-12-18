package com.bad_coders.moneyconverter.ViewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.bad_coders.moneyconverter.Adapter.RateAdapter;
import com.bad_coders.moneyconverter.BR;
import com.bad_coders.moneyconverter.Db.CurrencyDatabase;
import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.Model.RateFetcher;

import java.util.List;

import retrofit2.Response;

/**
 * Created on 17.11.2017.
 */

public class RateListViewModel extends BaseObservable
        implements RateFetcher.OnRateFetched {
    private RateFetcher mRateFetcher;
    private boolean isLoadFinished;
    private boolean isLoadSuccess;
    private RateAdapter mAdapter;
    private CurrencyDatabase mDatabase;

    public RateListViewModel(RateAdapter adapter, Context context) {
        mAdapter = adapter;
        mDatabase = CurrencyDatabase.newInstance(context);
        mRateFetcher = new RateFetcher(this);
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
        mDatabase.getCurrencyDao().deleteAll();
        mDatabase.getCurrencyDao().insertAll(rateList);
        isLoadFinished = true;
        isLoadSuccess = true;
        notifyLayout();
        mAdapter.swapList(rateList);
    }

    @Override
    public void onFailure() {
        List<Currency> rateList = mDatabase.getCurrencyDao().getList();
        isLoadFinished = true;
        if (rateList.size() != 0) {
            mAdapter.swapList(rateList);
            isLoadSuccess = true;
        } else isLoadSuccess = false;
        notifyLayout();
    }
}
