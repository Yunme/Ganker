package me.zzq.ganker.binding;

import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
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
        Glide.with(fragment).load("http://pic129.nipic.com/file/20170511/7138165_193428247000_2.jpg").into(imageView);
    }
}
