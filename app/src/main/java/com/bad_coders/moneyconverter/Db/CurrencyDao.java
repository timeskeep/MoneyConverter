package com.bad_coders.moneyconverter.Db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bad_coders.moneyconverter.Constants;
import com.bad_coders.moneyconverter.Model.Currency;

import java.util.List;

/**
 * Created on 21.11.2017.
 */

@Dao
public interface CurrencyDao {

    @Query("Select * from " + Constants.DB.TABLE_NAME)
    List<Currency> getList();

    @Insert
    void insertAll(List<Currency> currencies);

    @Query("Delete from " + Constants.DB.TABLE_NAME)
    void deleteAll();
}
