package me.zzq.ganker.ui.adapter;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import me.zzq.ganker.R;
import me.zzq.ganker.databinding.ItemDailyGanhuoBinding;
import me.zzq.ganker.vo.GanHuo;

/**
 * Created by zzq in 2017/7/24
 */

public class DailyGanHuoProvider extends ItemBindingProvider<ItemDailyGanhuoBinding, GanHuo> {

    private final DataBindingComponent dataBindingComponent;

    public DailyGanHuoProvider(DataBindingComponent dataBindingComponent) {
        this.dataBindingComponent = dataBindingComponent;
    }

    @Override
    protected ItemDailyGanhuoBinding createBinding(ViewGroup parent) {
        ItemDailyGanhuoBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_daily_ganhuo, parent,
                false, dataBindingComponent);
        return binding;
    }

    @Override
    protected void onBind(ItemDailyGanhuoBinding binding, GanHuo item) {
        binding.setGanHuo(item);
    }

}
