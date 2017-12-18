package com.bad_coders.moneyconverter.Ui;

import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bad_coders.moneyconverter.Manifest;
import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.ViewModel.MapsViewModel;
import com.bad_coders.moneyconverter.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created on 17.12.2017.
 */

public class MapsFragment extends Fragment implements OnMapReadyCallback {
    private static final String TAG = MapsFragment.class.getSimpleName();
    private MapsViewModel mMapsViewModel;

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMapsViewModel = new MapsViewModel(this);
        FragmentMapsBinding mapsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps,
                container, false);
        return mapsBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapsViewModel.onViewCreated();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMapsViewModel.onMapReady(googleMap);
    }
}
