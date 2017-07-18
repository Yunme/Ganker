package me.zzq.ganker.ui.adapter;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.zzq.ganker.R;
import me.zzq.ganker.databinding.ItemDailyBinding;
import me.zzq.ganker.util.Objects;
import me.zzq.ganker.vo.GanHuo;

/**
 * Created by zzq in 2017/7/18
 */

public class DailyImageAdapter extends DataBoundListAdapter<GanHuo, ItemDailyBinding> {

    private final DataBindingComponent dataBindingComponent;

    private final OnItemClickListener<GanHuo> onItemClickListener;

    @Inject
    public DailyImageAdapter(DataBindingComponent dataBindingComponent, OnItemClickListener<GanHuo> onItemClickListener) {
        this.dataBindingComponent = dataBindingComponent;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected ItemDailyBinding createBinding(ViewGroup parent) {
        final ItemDailyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_daily, parent, false, dataBindingComponent);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GanHuo ganHuo = binding.getGanHuo();
                if (ganHuo != null && onItemClickListener != null) {
                    onItemClickListener.onItemClick(0, ganHuo);
                }
            }
        });
        return binding;
    }

    @Override
    protected void bind(ItemDailyBinding binding, GanHuo item) {
        binding.setGanHuo(item);
    }

    @Override
    protected boolean areItemsTheSame(GanHuo oldItem, GanHuo newItem) {
        return Objects.equals(oldItem.getId(), newItem.getId());
    }

    @Override
    protected boolean areContentsTheSame(GanHuo oldItem, GanHuo newItem) {
        return Objects.equals(oldItem.getDesc(), newItem.getDesc());
    }
}
