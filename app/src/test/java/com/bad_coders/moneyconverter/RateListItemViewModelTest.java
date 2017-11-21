package com.bad_coders.moneyconverter;

import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.ViewModel.RateListItemViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * Created on 21.11.2017.
 */
public class RateListItemViewModelTest {
    private RateListItemViewModel mTarget;

    @Before
    public void setUp() throws Exception {
        mTarget = new RateListItemViewModel();
    }

    @Test
    public void isCorrectCurrencySet() {
        Currency currency = mock(Currency.class);
        mTarget.setCurrency(currency);
        assertThat(currency, is(mTarget.getCurrency()));
    }

}