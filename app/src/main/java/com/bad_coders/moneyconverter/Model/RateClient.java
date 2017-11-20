package com.bad_coders.moneyconverter.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created on 16.11.2017.
 */

public interface RateClient {
    @GET("exchange?json")
    Call<List<Currency>> getListOfExchangeRates();
}
