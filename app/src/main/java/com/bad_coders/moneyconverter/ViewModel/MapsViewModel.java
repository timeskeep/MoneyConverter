package com.bad_coders.moneyconverter.ViewModel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.bad_coders.moneyconverter.Model.BankResponse;
import com.bad_coders.moneyconverter.Model.BanksFetcher;
import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.Ui.DrawerActivity;
import com.bad_coders.moneyconverter.Ui.MapsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Response;

/**
 * Created on 17.12.2017.
 */

public class MapsViewModel implements BanksFetcher.OnBanksFetchedListener {
    private static final String TAG = MapsViewModel.class.getSimpleName();
    private static final int RC_PERMISSIONS = 32;
    private GoogleMap mMap;
    private MapsFragment mMapsFragment;
    private DrawerActivity mActivity;
    private LocationManager mLocationManager;
    private BanksFetcher mBanksFetcher;

    public MapsViewModel(MapsFragment mapsFragment) {
        mMapsFragment = mapsFragment;
        mActivity = (DrawerActivity) mapsFragment.getActivity();
    }

    public void onViewCreated() {
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) (mMapsFragment.getChildFragmentManager())
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(mMapsFragment);
        }
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int hasLocationPermission = mActivity
                    .checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                mActivity.requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        RC_PERMISSIONS);
                return;
            }
        }
        loadMaps();
    }

    @SuppressLint("MissingPermission")
    private void loadMaps() {
        mMap.setMyLocationEnabled(true);
        mLocationManager = (LocationManager) mActivity
                .getSystemService(Context.LOCATION_SERVICE);
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            LoadBanksAsyncTask task = new LoadBanksAsyncTask();
            task.execute();
        } else {
            Toast.makeText(mMapsFragment.getContext(), R.string.gps_error_msg,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSuccess(Response<BankResponse> response) {
        List<BankResponse.Bank> banks = response.body().getResults();
        for (BankResponse.Bank b : banks) {
            mMap.addMarker(new MarkerOptions()
                    .title(b.getName())
                    .snippet(b.getVicinity())
                    .position(new LatLng(
                            b.getGeometry().getLocation().getLat(),
                            b.getGeometry().getLocation().getLng()
                    )));
        }
    }

    @Override
    public void onFailure() {
        Toast.makeText(mMapsFragment.getContext(), R.string.error_msg, Toast.LENGTH_LONG).show();
    }

    private class LoadBanksAsyncTask extends AsyncTask<Void, Location, Location> {
        @SuppressLint("MissingPermission")
        @Override
        protected Location doInBackground(Void... voids) {
            Location location = null;
            Criteria criteria = new Criteria();
            String provider = mLocationManager.getBestProvider(criteria, true);
            while (location == null) {
                location = mLocationManager.getLastKnownLocation(provider);
            }
            return location;
        }

        @Override
        protected void onPostExecute(Location location) {
            if (location != null) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                LatLng latLng = new LatLng(lat, lng);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                mBanksFetcher =
                        new BanksFetcher(MapsViewModel.this, mMapsFragment.getContext());
                mBanksFetcher.fetch(lat, lng, mMap.getCameraPosition().zoom);
            }
        }
    }
}
