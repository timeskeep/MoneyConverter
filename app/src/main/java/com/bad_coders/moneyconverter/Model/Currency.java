package com.bad_coders.moneyconverter.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created on 16.11.2017.
 */

public class Currency implements Serializable {
    @SerializedName("txt")
    private String name;
    @SerializedName("rate")
    private double rate;
    @SerializedName("cc")
    private String curSymb;

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
}
