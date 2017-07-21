package me.zzq.ganker.ui.adapter;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

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
                    binding.dailyImageView.setTransitionName("transition" + items.indexOf(ganHuo));
                    onItemClickListener.onItemClick(binding.dailyImageView, items.indexOf(ganHuo), ganHuo);
                }
            }
        });

        return binding;
    }

    @Override
    protected void bind(final ItemDailyBinding binding, GanHuo item) {
        binding.setGanHuo(item);
        Glide.with(binding.getRoot().getContext())
                .load("http://pic129.nipic.com/file/20170511/7138165_193428247000_2.jpg")
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Palette.Swatch swatch = getImagePaletteSwatch(((GlideBitmapDrawable) resource).getBitmap());
                        if (swatch != null) {
                            binding.textView.setBackgroundColor(swatch.getRgb());
                            binding.textView.setTextColor(swatch.getTitleTextColor());
                        }
                        return false;
                    }
                })
                .into(binding.dailyImageView);
    }

    @Override
    protected boolean areItemsTheSame(GanHuo oldItem, GanHuo newItem) {
        return Objects.equals(oldItem.getId(), newItem.getId());
    }

    @Override
    protected boolean areContentsTheSame(GanHuo oldItem, GanHuo newItem) {
        return Objects.equals(oldItem.getDesc(), newItem.getDesc());
    }

    private Palette.Swatch getImagePaletteSwatch(Bitmap bitmap) {

        Palette palette = Palette.from(bitmap).generate();
        Palette.Swatch swatch = palette.getVibrantSwatch();
        if (swatch != null) {
            return swatch;
        }
        return null;
    }
}
