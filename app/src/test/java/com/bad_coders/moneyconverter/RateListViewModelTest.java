package com.bad_coders.moneyconverter;

import android.view.View;

import com.bad_coders.moneyconverter.ViewModel.RateListViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

/**
 * Created on 22.11.2017.
 */
public class RateListViewModelTest {
    private RateListViewModel mTarget;

    @Before
    public void setUp() throws Exception {
        mTarget = mock(RateListViewModel.class);
    }

    @Test
    public void verifyRefreshStarted() {
        mTarget.onTryAgainButtonClick(mock(View.class));
        assertThat(mTarget.isLoadFinished(), is(false));
        assertThat(mTarget.isLoadSuccess(), is(false));
    }
}