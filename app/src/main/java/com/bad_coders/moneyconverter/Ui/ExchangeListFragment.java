package com.bad_coders.moneyconverter.Ui;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bad_coders.moneyconverter.Adapter.RateAdapter;
import com.bad_coders.moneyconverter.BuildConfig;
import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.ViewModel.RateListViewModel;
import com.bad_coders.moneyconverter.databinding.FragmentExchangeListBinding;

public class ExchangeListFragment extends Fragment {

    public static ExchangeListFragment newInstance() {
        ExchangeListFragment fragment = new ExchangeListFragment();
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
        RateListViewModel rateBox = new RateListViewModel(rateAdapter, getContext());
        binding.setRatebox(rateBox);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(rateAdapter);
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                new AlertDialog.Builder(getContext())
                        .setTitle("About")
                        .setMessage(getString(R.string.version_info, String.valueOf(BuildConfig.VERSION_NAME), BuildConfig.BUILD_TYPE))
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
