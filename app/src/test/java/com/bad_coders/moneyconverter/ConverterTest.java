package com.bad_coders.moneyconverter;

import com.bad_coders.moneyconverter.Model.Converter;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created on 20.11.2017.
 */
public class ConverterTest {
    private Converter mTarget;
    private double rate = 31.2;

    @Before
    public void setUp() throws Exception {
        mTarget = new Converter(rate, "EUR", "UAH");
    }

    @Test
    public void reverseCalculationIsCorrect() {
        double amount = 2;
        mTarget.swapCurrencies();
        mTarget.setAmount(amount);
        assertThat(mTarget.getResult(), is(amount / rate));
    }

    @Test
    public void calculationIsCorrect() {
        double amount = 4.5;
        mTarget.setAmount(amount);
        assertThat(mTarget.getResult(), is(amount * rate));
    }
}