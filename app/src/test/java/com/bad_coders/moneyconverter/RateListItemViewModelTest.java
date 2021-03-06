package com.bad_coders.moneyconverter;

import android.test.mock.MockContext;

import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.ViewModel.ItemViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * Created on 21.11.2017.
 */
public class RateListItemViewModelTest {
    private ItemViewModel mTarget;

    @Before
    public void setUp() throws Exception {
        MockContext context = new MockContext();
        mTarget = new ItemViewModel(context);
        MockitoAnnotations.initMocks(Currency.class);
    }

    @Test
    public void isCorrectCurrencySet() {
        Currency currency = mock(Currency.class);
        mTarget.setCurrency(currency);
        assertThat(currency, is(mTarget.getCurrency()));
    }

    @Test
    public void isNameNull(){
        Currency currency = mock(Currency.class);
        assertThat(null, is(currency.getName()));
    }

}