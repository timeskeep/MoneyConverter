package com.bad_coders.moneyconverter;

import android.test.mock.MockContext;

import com.bad_coders.moneyconverter.Model.Converter;
import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.ViewModel.ConverterViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Created on 20.11.2017.
 */
public class ConverterViewModelTest {
    private ConverterViewModel mTarget;
    private Converter mConverter;

    @Before
    public void setUp() throws Exception {
        MockContext context = new MockContext();
        mTarget = new ConverterViewModel(new Currency("US Dollar", 26.8, "USD"), context);
        mConverter = mock(Converter.class);
        mTarget.setConverter(mConverter);
    }

    @Test
    public void callsSwapCurrencies() {
        mTarget.onSwapFabClick();
        verify(mConverter).swapCurrencies();
    }

}