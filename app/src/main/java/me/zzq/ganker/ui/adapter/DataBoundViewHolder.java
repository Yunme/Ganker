package me.zzq.ganker.ui.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by zzq in 2017/7/18
 * <p>
 * A generic ViewHolder that works with a {@link ViewDataBinding}.
 *
 * @param <T> The type of the ViewDataBinding.
 */

public class DataBoundViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public final T binding;

    public DataBoundViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
