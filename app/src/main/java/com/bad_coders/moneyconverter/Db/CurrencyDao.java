package com.bad_coders.moneyconverter.Db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.bad_coders.moneyconverter.Utils.Constants;
import com.bad_coders.moneyconverter.Model.Currency;

import java.util.List;

/**
 * Created on 21.11.2017.
 */

@Dao
public interface CurrencyDao {

    @Query("Select * from " + Constants.DB.TABLE_NAME + " order by " + Constants.DB.SYMBOL_COLUMN)
    List<Currency> getList();

    @Insert
    void insertAll(List<Currency> currencies);

    @Query("Delete from " + Constants.DB.TABLE_NAME)
    void deleteAll();

    @Query("Select " + Constants.DB.SYMBOL_COLUMN +
            " from " + Constants.DB.TABLE_NAME)
    List<String> getCurSymbols();

    @Query("Select " + Constants.DB.RATE_COLUMN + " from " + Constants.DB.TABLE_NAME + " where " +
            Constants.DB.SYMBOL_COLUMN + " = :curSymbol")
    double getRate(String curSymbol);

    @Query("Select " + Constants.DB.NAME_COLUMN + " from " + Constants.DB.TABLE_NAME + " where " +
            Constants.DB.SYMBOL_COLUMN + " = :curSymbol")
    String getCurName(String curSymbol);
}
