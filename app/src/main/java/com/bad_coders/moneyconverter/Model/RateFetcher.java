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
    private OnRateFetched mOnRateFetched;

    public RateFetcher(OnRateFetched onRateFetched) {
        mOnRateFetched = onRateFetched;
    }

    public void loadRateList() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        RateClient rateClient = retrofit.create(RateClient.class);
        Call<List<Currency>> call = rateClient.getListOfExchangeRates();
        RateCallback callback = new RateCallback(call);

        call.enqueue(callback);
    }

    public interface OnRateFetched {
        void onResponse(Response<List<Currency>> response);

        void onFailure(Throwable t);
    }

    private class RateCallback implements Callback<List<Currency>> {
        private Random mRandom = new Random();
        private int mMaxRetries = 5;
        private Call<List<Currency>> mCall;
        private ScheduledExecutorService mScheduledExecutorService;
        private int mNumOfRetries;

        RateCallback(Call<List<Currency>> call) {
            this(call, 0);
        }

        private RateCallback(Call<List<Currency>> call, int numOfRetries) {
            mCall = call;
            mNumOfRetries = numOfRetries;
            mScheduledExecutorService = Executors.newScheduledThreadPool(1);
        }

        @Override
        public void onResponse(@NonNull Call<List<Currency>> call,
                               @NonNull Response<List<Currency>> response) {
            mOnRateFetched.onResponse(response);
        }

        @Override
        public void onFailure(@NonNull Call<List<Currency>> call, @NonNull Throwable t) {
            if (mNumOfRetries < mMaxRetries) retryCall();
            else mOnRateFetched.onFailure(t);
        }

        private void retryCall() {
            final int interval = (1 << mNumOfRetries) * 1000 + mRandom.nextInt(1001);
            mScheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    Call<List<Currency>> call = mCall.clone();
                    Log.e(TAG, "Number of retry: " + (mNumOfRetries + 1)
                            + ". Next retry in " + interval + " milliseconds.");
                    call.enqueue(new RateCallback(call, mNumOfRetries + 1));
                }
            }, interval, TimeUnit.MILLISECONDS);
        }
    }
}
