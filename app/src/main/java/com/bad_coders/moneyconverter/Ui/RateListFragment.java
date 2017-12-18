package com.bad_coders.moneyconverter.Ui;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bad_coders.moneyconverter.Adapter.RateAdapter;
import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.ViewModel.RateListViewModel;
import com.bad_coders.moneyconverter.databinding.FragmentExchangeListBinding;

public class RateListFragment extends Fragment {

    public static RateListFragment newInstance() {
        RateListFragment fragment = new RateListFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentExchangeListBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_exchange_list, container, false);
        RateAdapter rateAdapter = new RateAdapter(getContext());
        RateListViewModel rateBox = new RateListViewModel(rateAdapter, (DrawerActivity) getActivity());
        binding.setRatebox(rateBox);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(rateAdapter);
        return binding.getRoot();
    }
}
