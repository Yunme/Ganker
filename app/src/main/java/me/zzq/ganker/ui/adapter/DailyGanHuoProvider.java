package me.zzq.ganker.ui.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import me.zzq.ganker.R;
import me.zzq.ganker.databinding.ItemDailyGanhuoBinding;

/**
 * Created by zzq in 2017/7/24
 */

public class DailyGanHuoProvider extends ItemBindingProvider<ItemDailyGanhuoBinding, GanHuoImage> {

    @Override
    protected ItemDailyGanhuoBinding createBinding(ViewGroup parent) {
        ItemDailyGanhuoBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_daily_ganhuo, parent,
                false);
        return binding;
    }

    @Override
    protected void onBind(ItemDailyGanhuoBinding binding, GanHuoImage item) {

        binding.textTitle.setText(item.getTitle());
        binding.textWho.setText("[" + item.getWho() + "]");
    }

}
