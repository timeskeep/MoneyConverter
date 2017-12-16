package com.bad_coders.moneyconverter.Ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.ViewModel.AboutViewModel;
import com.bad_coders.moneyconverter.databinding.FragmentAboutBinding;

/**
 * Created on 15.12.2017.
 */

public class AboutFragment extends Fragment {
    private static final String TAG = AboutFragment.class.getSimpleName();

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAboutBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_about, container, false);
        AboutViewModel aboutViewModel = new AboutViewModel(this);
        binding.setViewModel(aboutViewModel);
        return binding.getRoot();
    }
}
