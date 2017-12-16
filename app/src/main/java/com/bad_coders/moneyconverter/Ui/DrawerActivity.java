package com.bad_coders.moneyconverter.Ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.ViewModel.DrawerViewModel;
import com.bad_coders.moneyconverter.databinding.ActivityDrawerBinding;
import com.bad_coders.moneyconverter.databinding.NavViewHeaderBinding;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created on 15.12.2017.
 */

public abstract class DrawerActivity extends BaseActivity {
    private ActivityDrawerBinding mBinding;
    private DrawerViewModel mViewModel;
    private FirebaseAuth mAuth;
    private static final int RC_SIGNIN = 5;

    @Override
    public void setLayout() {
        mBinding = DataBindingUtil.setContentView(
                this,
                R.layout.activity_drawer
        );
        NavigationView navigationView = mBinding.navView;
        NavViewHeaderBinding headerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_view_header,
                navigationView, false);
        mBinding.navView.addHeaderView(headerBinding.getRoot());
        Toolbar toolbar = mBinding.activityBase.toolbar;
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = mBinding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.action_open_drawer, R.string.action_close_drawer
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mViewModel = new DrawerViewModel(this, drawerLayout, navigationView, headerBinding);
        navigationView.setNavigationItemSelectedListener(mViewModel);
        headerBinding.setViewmodel(mViewModel);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = mBinding.drawerLayout;
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(mViewModel);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuth.removeAuthStateListener(mViewModel);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mViewModel.onActivityResult(requestCode, resultCode, data);
    }
}
