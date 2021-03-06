package me.zzq.ganker.binding;

import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

/**
 * Created by zzq in 2017/7/18
 * Binding Adapters that work with a fragment instance.
 */

public class FragmentBindingAdapters {
    final Fragment fragment;

    @Inject
    public FragmentBindingAdapters(Fragment fragment) {
        this.fragment = fragment;
    }

    @BindingAdapter("imageUrl")
    public void bindImage(ImageView imageView, String url) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(fragment)
                    .load(url)
                    .crossFade()
                    .into(imageView);
        }
    }
}
