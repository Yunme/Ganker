package me.zzq.ganker.ui.adapter;

import android.view.View;

/**
 * Created by zzq in 2017/7/18
 */

public interface OnItemClickListener<T> {
    void onItemClick(View view, int position, T item);
}
