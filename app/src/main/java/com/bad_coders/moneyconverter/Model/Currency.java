package com.bad_coders.moneyconverter.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static com.bad_coders.moneyconverter.Constants.DB.TABLE_NAME;

/**
 * Created on 16.11.2017.
 */

@Entity(tableName = TABLE_NAME)
public class Currency implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "_symbol")
    @SerializedName("cc")
    @NonNull
    private String curSymb;

    @ColumnInfo(name = "name")
    @SerializedName("txt")
    private String name;

    @ColumnInfo(name = "rate")
    @SerializedName("rate")
    private double rate;

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

    @Override
    public boolean equals(Object o) {
        return o instanceof Currency && ((Currency) o).curSymb.equals(curSymb) && ((Currency) o).rate == rate;
    }
}
