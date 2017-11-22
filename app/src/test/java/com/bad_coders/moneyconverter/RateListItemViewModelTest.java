package com.bad_coders.moneyconverter;

import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.ViewModel.RateListItemViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created on 21.11.2017.
 */
public class RateListItemViewModelTest {
    private RateListItemViewModel mTarget;

    @Before
    public void setUp() throws Exception {
        mTarget = new RateListItemViewModel();
        MockitoAnnotations.initMocks(Currency.class);
    }

    @Test
    public void isCorrectCurrencySet() {
        Currency currency = mock(Currency.class);
        mTarget.setCurrency(currency);
        assertThat(currency, is(mTarget.getCurrency()));
    }

    @Test
    public void isNameEmpty(){
    Currency currency = mock(Currency.class);
    when(currency.getName().equals("")).thenReturn(false);
    assertThat("",currency.getName().equals(""));
    }

}