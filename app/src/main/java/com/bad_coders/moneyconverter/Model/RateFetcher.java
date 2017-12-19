package com.bad_coders.moneyconverter.Model;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 17.12.2017.
 */

public class RateFetcher {
    private static final String TAG = RateFetcher.class.getSimpleName();
    private static final String DOMAIN_NAME =
            "https://bank.gov.ua/NBUStatService/v1/statdirectory/";
    private ExponentialBackOffCallback.OnFetchListener mListener;

    public RateFetcher(ExponentialBackOffCallback.OnFetchListener listener) {
        mListener = listener;
    }

    public void loadRateList() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        RateClient rateClient = retrofit.create(RateClient.class);
        Call<List<Currency>> call = rateClient.getListOfExchangeRates();
        ExponentialBackOffCallback<List<Currency>> callback = new ExponentialBackOffCallback<>(call,
                mListener, Executors.newScheduledThreadPool(1));
        call.enqueue(callback);
    }
}
