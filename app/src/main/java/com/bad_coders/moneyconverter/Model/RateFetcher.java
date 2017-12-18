package com.bad_coders.moneyconverter.Model;

import android.support.annotation.NonNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 17.12.2017.
 */

public class RateFetcher {
    private static final String DOMAIN_NAME =
            "https://bank.gov.ua/NBUStatService/v1/statdirectory/";
    private OnRateFetched mOnRateFetched;

    public RateFetcher(OnRateFetched onRateFetched) {
        mOnRateFetched = onRateFetched;
    }

    public void loadRateList() {
        RateCallback callback = new RateCallback();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        RateClient rateClient = retrofit.create(RateClient.class);
        Call<List<Currency>> call = rateClient.getListOfExchangeRates();

        call.enqueue(callback);
    }

    public interface OnRateFetched {
        void onResponse(Response<List<Currency>> response);
        void onFailure();
    }

    private class RateCallback implements Callback<List<Currency>> {
        @Override
        public void onResponse(@NonNull Call<List<Currency>> call, @NonNull Response<List<Currency>> response) {
            mOnRateFetched.onResponse(response);
        }

        @Override
        public void onFailure(@NonNull Call<List<Currency>> call, @NonNull Throwable t) {
            mOnRateFetched.onFailure();
        }
    }
}
