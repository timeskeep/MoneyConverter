package com.bad_coders.moneyconverter.ViewModel;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bad_coders.moneyconverter.BR;
import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.Ui.AboutFragment;
import com.bad_coders.moneyconverter.Ui.DrawerActivity;
import com.bad_coders.moneyconverter.Ui.ExchangeListFragment;
import com.bad_coders.moneyconverter.databinding.NavViewHeaderBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * Created on 15.12.2017.
 */

public class DrawerViewModel extends BaseObservable implements NavigationView.OnNavigationItemSelectedListener,
        FirebaseAuth.AuthStateListener {
    private static final String TAG = DrawerViewModel.class.getSimpleName();
    private static final int RC_SIGNIN = 5;

    private String tags[] = {ExchangeListFragment.class.getSimpleName(),
            AboutFragment.class.getSimpleName()};

    private DrawerActivity mActivity;
    private NavViewHeaderBinding mHeaderBinding;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private FragmentManager mFragmentManager;

    private boolean isAuthorised;
    private boolean mNavigationExpanded = false;
    private FirebaseUser mUser;

    public DrawerViewModel(DrawerActivity activity, DrawerLayout drawer,
                           NavigationView navigationView, NavViewHeaderBinding headerBinding) {
        mActivity = activity;
        mDrawer = drawer;
        mNavigationView = navigationView;
        mFragmentManager = mActivity.getSupportFragmentManager();
        mHeaderBinding = headerBinding;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        boolean isFragmentShown = false;
        switch (id) {
            case R.id.rate_list_fragment:
                isFragmentShown = getFragment(ExchangeListFragment.class);
                break;
            case R.id.about_fragment:
                isFragmentShown = getFragment(AboutFragment.class);
                break;
            case R.id.action_login:
                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                        new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()
                );
                mActivity.startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers).build(), RC_SIGNIN);
                break;
            case R.id.action_logout:
                AuthUI.getInstance().signOut(mActivity);
                setMenu(R.menu.login_menu);
                break;
        }
        if (!isFragmentShown) return false;
        mActivity.setTitle(item.getTitle());
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean getFragment(Class fragmentClass) {
        String fragmentTag = fragmentClass.getSimpleName();
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mFragmentManager.beginTransaction().add(R.id.container, fragment, fragmentTag).commit();
            return showFragment(mFragmentManager, fragment);
        } else return showFragment(mFragmentManager, fragment);
    }

    private boolean showFragment(FragmentManager fragmentManager, Fragment fragment) {
        boolean isShown = false;
        for (String tag : tags) {
            if (!Objects.equals(fragment.getTag(), tag)) {
                Fragment fragmentToHide = fragmentManager.findFragmentByTag(tag);
                if (fragmentToHide != null)
                    fragmentManager.beginTransaction().hide(fragmentToHide).commit();
            } else {
                fragmentManager.beginTransaction().show(fragment).commit();
                isShown = true;
            }
        }
        return isShown;
    }

    public void onDropdownClick(View view) {
        view.animate().rotation(mNavigationExpanded ? 0 : 180).start();
        mNavigationExpanded = !mNavigationExpanded;
        setMenu(mNavigationExpanded ?
                !isAuthorised ? R.menu.login_menu : R.menu.logout_menu : R.menu.drawer_menu);
    }

    @Override
    public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
        mUser = firebaseAuth.getCurrentUser();
        isAuthorised = mUser != null;
        notifyPropertyChanged(BR.authorised);
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(final ImageView view, Uri uri) {
        if (uri == null) {
            view.setImageResource(R.drawable.ic_account_circle_white_48dp);
            return;
        }
        Picasso.with(view.getContext())
                .load(uri).placeholder(R.drawable.ic_account_circle_white_48dp)
                .into(view, new Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap imageBitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();
                        RoundedBitmapDrawable imageDrawable =
                                RoundedBitmapDrawableFactory.create(view.getResources(), imageBitmap);
                        imageDrawable.setCircular(true);
                        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
                        view.setImageDrawable(imageDrawable);
                    }

                    @Override
                    public void onError() {
                        Log.e(TAG, "Failed to load the image!");
                    }
                });
    }

    @Bindable
    public boolean isAuthorised() {
        return isAuthorised;
    }

    @Bindable
    public FirebaseUser getUser() {
        return mUser;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGNIN) {
            if (resultCode == RESULT_OK) {
                setMenu(R.menu.logout_menu);
            }
        }
    }

    private void setMenu(int logout_menu) {
        mNavigationView.getMenu().clear();
        mNavigationView.inflateMenu(logout_menu);
    }
}
