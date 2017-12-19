package com.bad_coders.moneyconverter.Model;

import android.content.Context;
import android.util.Log;

import com.bad_coders.moneyconverter.R;

import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 17.12.2017.
 */

public class BanksFetcher {
    private static final String TAG = BanksFetcher.class.getSimpleName();
    private static final String DOMAIN_NAME = "https://maps.googleapis.com/";
    private ExponentialBackOffCallback.OnFetchListener<BankResponse> mListener;
    private Context mContext;

    public BanksFetcher(ExponentialBackOffCallback.OnFetchListener<BankResponse> onBanksFetchedListener,
                        Context context) {
        mListener = onBanksFetchedListener;
        mContext = context;
    }

    public void fetch(double lng, double lat, float zoom) {
        String coordinates = lng + "," + lat;
        int radius = (int) ((21 - zoom) * 100);
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(DOMAIN_NAME)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        BanksClient rateClient = retrofit.create(BanksClient.class);
        Call<BankResponse> call = rateClient.getListOfBanks(coordinates, mContext.getString(R.string.bank_key), radius,
                mContext.getString(R.string.google_maps_key));
        call.enqueue(new ExponentialBackOffCallback<BankResponse>(call, mListener,
                Executors.newScheduledThreadPool(1)));
    }
}
