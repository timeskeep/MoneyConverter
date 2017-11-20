package com.bad_coders.moneyconverter.ViewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.view.View;

import com.bad_coders.moneyconverter.BR;
import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.Model.RateClient;
import com.bad_coders.moneyconverter.RateAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 17.11.2017.
 */

public class RateListViewModel extends BaseObservable {
    public static final String DOMAIN_NAME = "https://bank.gov.ua/NBUStatService/v1/statdirectory/";
    private boolean isLoadFinished;
    private boolean isLoadSuccess;
    private RateAdapter mAdapter;

    public RateListViewModel(RateAdapter adapter) {
        mAdapter = adapter;
        loadRateList();
    }

    private void loadRateList() {
        RateCallback callback = new RateCallback();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        RateClient rateClient = retrofit.create(RateClient.class);
        Call<List<Currency>> call = rateClient.getListOfExchangeRates();

        call.enqueue(callback);
    }

    private class RateCallback implements Callback<List<Currency>> {
        @Override
        public void onResponse(@NonNull Call<List<Currency>> call, @NonNull Response<List<Currency>> response) {
            List<Currency> rateList = response.body();
            isLoadFinished = true;
            isLoadSuccess = true;
            notifyLayout();
            mAdapter.swapList(rateList);
        }

        @Override
        public void onFailure(@NonNull Call<List<Currency>> call, @NonNull Throwable t) {
            isLoadFinished = true;
            isLoadSuccess = false;
            notifyLayout();
        }
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

    public void onTryAgainButtonClick(View view) {
        isLoadFinished = false;
        isLoadSuccess = false;
        notifyLayout();
        loadRateList();
    }
}
