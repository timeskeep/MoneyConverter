package com.bad_coders.moneyconverter.Ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.ViewModel.ConverterViewModel;
import com.bad_coders.moneyconverter.databinding.FragmentConverterBinding;

/**
 * Created on 20.11.2017.
 */

public class ConverterFragment extends Fragment {
    private static final String CURRENCY_TAG = "currency";

    public static ConverterFragment newInstance(Currency currency) {
        Bundle args = new Bundle();
        args.putSerializable(CURRENCY_TAG, currency);

        ConverterFragment fragment = new ConverterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((ConverterActivity) getActivity()).getSupportActionBar().setTitle(R.string.converter_label);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentConverterBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_converter, container, false);
        Currency currency = (Currency) getArguments().getSerializable(CURRENCY_TAG);
        ConverterViewModel viewModel = new ConverterViewModel(currency, getContext());
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}
