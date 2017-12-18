package com.bad_coders.moneyconverter.Model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created on 17.12.2017.
 */

public interface BanksClient {
    @GET("maps/api/place/nearbysearch/json")
    Call<BankResponse> getListOfBanks(@Query("location") String location,
                                    @Query("types") String type,
                                    @Query("radius") int radius,
                                    @Query("key") String key);
}
