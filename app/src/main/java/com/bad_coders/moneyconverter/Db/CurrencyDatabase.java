package com.bad_coders.moneyconverter.Db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.bad_coders.moneyconverter.Model.Currency;

/**
 * Created on 21.11.2017.
 */

@Database(entities = {Currency.class}, version = 1)
public abstract class CurrencyDatabase extends RoomDatabase {
    public abstract CurrencyDao getCurrencyDao();

    private static CurrencyDatabase sDatabase;

    public static CurrencyDatabase newInstance(Context context) {
        if (sDatabase == null)
            //TODO: Disallow Main Thread Queries
            sDatabase = Room.databaseBuilder(context, CurrencyDatabase.class, "rate.db")
                    .allowMainThreadQueries().build();
        return sDatabase;
    }
}
