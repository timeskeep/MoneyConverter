package com.bad_coders.moneyconverter.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bad_coders.moneyconverter.Model.Currency;
import com.bad_coders.moneyconverter.R;
import com.bad_coders.moneyconverter.ViewModel.ItemViewModel;
import com.bad_coders.moneyconverter.databinding.RateItemBinding;

import java.util.List;

/**
 * Created on 16.11.2017.
 */

public class RateAdapter extends RecyclerView.Adapter<RateAdapter.ViewHolder> {
    public static final String TAG = RateAdapter.class.getSimpleName();
    private Context mContext;
    private List<Currency> mRateList;

    public RateAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        RateItemBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.rate_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mRateList.get(position));
    }

    @Override
    public int getItemCount() {
        return mRateList == null ? 0 : mRateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public RateItemBinding mBinding;

        public ViewHolder(RateItemBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setRateViewModel(new ItemViewModel(mContext));
        }

        public void bind(Currency currency) {
            SharedPreferences sharedPreferences =
                    mContext.getSharedPreferences(mContext.getApplicationInfo().packageName + "_preferences",
                            Context.MODE_PRIVATE);
            String rateBaseSymb = sharedPreferences.getString("currency_base", "UAH");
            double rateBase = sharedPreferences.getFloat("rate_base", 1);
            currency.setRate(currency.getRate() / rateBase);
            currency.setBaseCurSymb(rateBaseSymb);
            mBinding.getRateViewModel().setCurrency(currency);
            mBinding.executePendingBindings();
        }
    }

    public List<Currency> swapList(List<Currency> currencies) {
        if (currencies == null) return null;
        List<Currency> oldCurrencies = mRateList;
        mRateList = currencies;
        notifyDataSetChanged();
        return oldCurrencies;
    }
}
