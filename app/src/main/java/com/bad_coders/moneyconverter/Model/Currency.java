package com.bad_coders.moneyconverter.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.bad_coders.moneyconverter.Constants;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static com.bad_coders.moneyconverter.Constants.DB.TABLE_NAME;

/**
 * Created on 16.11.2017.
 */

@Entity(tableName = TABLE_NAME)
public class Currency implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = Constants.DB.SYMBOL_COLUMN)
    @SerializedName("cc")
    @NonNull
    private String curSymb;

    @ColumnInfo(name = Constants.DB.NAME_COLUMN)
    @SerializedName("txt")
    private String name;

    @ColumnInfo(name = Constants.DB.RATE_COLUMN)
    @SerializedName("rate")
    private double rate;

    @ColumnInfo(name = Constants.DB.BASE_CUR_NAME_COLUMN)
    private String baseCurSymb;

    public Currency(String name, double rate, String curSymb) {
        this.name = name;
        this.rate = rate;
        this.curSymb = curSymb;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }

    public String getCurSymb() {
        return curSymb;
    }

    public String getBaseCurSymb() {
        return baseCurSymb;
    }

    public void setRate(double rate) {
        baseCurSymb = "UAH";
        this.rate = rate;
    }

    public void setBaseCurSymb(String baseCurSymb) {
        this.baseCurSymb = baseCurSymb;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Currency && ((Currency) o).curSymb.equals(curSymb) && ((Currency) o).rate == rate;
    }
}
