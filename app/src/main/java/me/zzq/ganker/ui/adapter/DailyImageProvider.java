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
import me.zzq.ganker.vo.GanHuo;

/**
 * Created by zzq in 2017/7/24
 */

public class DailyImageProvider extends ItemBindingProvider<ItemDailyBinding, GanHuo> {

    private final DataBindingComponent dataBindingComponent;

    private final OnItemClickListener<GanHuo> onItemClickListener;

    public DailyImageProvider(DataBindingComponent dataBindingComponent, OnItemClickListener<GanHuo> onItemClickListener) {
        this.dataBindingComponent = dataBindingComponent;
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    protected ItemDailyBinding createBinding(ViewGroup parent) {
        final ItemDailyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_daily, parent, false, dataBindingComponent);
        return binding;
    }

    @Override
    protected void onBind(final ItemDailyBinding itemDailyBinding, final GanHuo ganHuo, final int position) {
        itemDailyBinding.setGanHuo(ganHuo);
        itemDailyBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ganHuo != null && onItemClickListener != null) {
                    itemDailyBinding.dailyImageView.setTransitionName("transition" + ganHuo);
                    onItemClickListener.onItemClick(itemDailyBinding.dailyImageView, position, ganHuo);
                }
            }
        });
        Glide.with(itemDailyBinding.getRoot().getContext())
                .load(ganHuo.getUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Palette.Swatch swatch = getImagePaletteSwatch(((GlideBitmapDrawable) resource).getBitmap());
                        if (swatch != null) {
                            itemDailyBinding.textTitle.setTextColor(swatch.getTitleTextColor());
                            itemDailyBinding.textDesc.setBackgroundColor(swatch.getRgb());
                            itemDailyBinding.textDesc.setTextColor(swatch.getTitleTextColor());
                            itemDailyBinding.cardView.setCardBackgroundColor(swatch.getRgb());
                        }
                        return false;
                    }
                })
                .into(itemDailyBinding.dailyImageView);
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
