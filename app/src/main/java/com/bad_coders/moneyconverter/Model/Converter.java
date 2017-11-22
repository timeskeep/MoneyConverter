package com.bad_coders.moneyconverter.Model;

/**
 * Created on 20.11.2017.
 */

public class Converter {
    private double amount;
    private double rate;
    private String curSaleSymb;
    private String curPurchaseSymb;

    public Converter(double rate, String curSaleSymb, String curPurchaseSymb) {
        this.rate = rate;
        this.curSaleSymb = curSaleSymb;
        this.curPurchaseSymb = curPurchaseSymb;
    }

    public String getCurSaleSymb() {
        return curSaleSymb;
    }

    public String getCurPurchaseSymb() {
        return curPurchaseSymb;
    }

    public double getRate() {
        return rate;
    }

    public double getResult() {
        return amount * rate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void swapCurrencies() {
        String tempSymb = curPurchaseSymb;
        curPurchaseSymb = curSaleSymb;
        curSaleSymb = tempSymb;
        rate = 1 / rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
