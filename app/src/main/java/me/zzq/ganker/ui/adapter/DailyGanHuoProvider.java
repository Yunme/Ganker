package me.zzq.ganker.ui.adapter;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.zzq.ganker.R;
import me.zzq.ganker.databinding.ItemDailyGanhuoBinding;
import me.zzq.ganker.vo.GanHuo;

/**
 * Created by zzq in 2017/7/24
 */

public class DailyGanHuoProvider extends ItemBindingProvider<ItemDailyGanhuoBinding, GanHuo> {

    private final DataBindingComponent dataBindingComponent;

    private OnItemClickListener<GanHuo> onItemClickListener;

    public DailyGanHuoProvider(DataBindingComponent dataBindingComponent, OnItemClickListener<GanHuo> onItemClickListener) {
        this.dataBindingComponent = dataBindingComponent;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected ItemDailyGanhuoBinding createBinding(ViewGroup parent) {
        ItemDailyGanhuoBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_daily_ganhuo, parent,
                false, dataBindingComponent);
        return binding;
    }

    @Override
    protected void onBind(ItemDailyGanhuoBinding binding, final GanHuo item, final int position) {
        binding.setGanHuo(item);
        binding.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, position, item);
                }
            }
        });
    }

}
