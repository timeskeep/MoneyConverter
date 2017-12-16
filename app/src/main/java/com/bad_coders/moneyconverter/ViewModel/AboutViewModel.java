package com.bad_coders.moneyconverter.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.bad_coders.moneyconverter.BuildConfig;
import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.Ui.AboutFragment;
import com.bad_coders.moneyconverter.Ui.MainActivity;

/**
 * Created on 26.11.2017.
 */

public class AboutViewModel {
    private String mVersion;
    private Context mContext;
    private MainActivity mActivity;

    public AboutViewModel(AboutFragment aboutFragment) {
        mVersion = BuildConfig.VERSION_NAME;
        mContext = aboutFragment.getContext();
        mActivity = (MainActivity) aboutFragment.getActivity();
    }

    public String getVersion() {
        return mVersion;
    }

    public void onFeedbackButtonClick() {
        Uri uri = Uri.fromParts(mContext.getString(R.string.send_feedback_protocol),
                mContext.getString(R.string.feedback_email_address), null);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, mContext.getString(R.string.subject));
        PackageManager packageManager = mActivity.getPackageManager();
        if (emailIntent.resolveActivity(packageManager) != null)
            mContext.startActivity(Intent.createChooser(emailIntent, mContext.getString(R.string.send_email)));
        else Toast.makeText(mContext, R.string.email_intent_resolve_error,
                Toast.LENGTH_LONG).show();
    }
}
