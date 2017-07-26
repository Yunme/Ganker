package me.zzq.ganker.ui.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import me.zzq.ganker.R;
import me.zzq.ganker.databinding.ItemDailyTitleBinding;

/**
 * Created by zzq in 2017/7/26
 */

public class DailyTitleProvider extends ItemBindingProvider<ItemDailyTitleBinding, String> {
    @Override
    protected ItemDailyTitleBinding createBinding(ViewGroup parent) {
        ItemDailyTitleBinding titleBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_daily_title, parent, false);
        return titleBinding;
    }

    @Override
    protected void onBind(ItemDailyTitleBinding binding, String item) {
        binding.setTitle(item);
    }
}
