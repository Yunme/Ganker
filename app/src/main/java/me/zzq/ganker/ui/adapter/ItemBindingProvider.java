package me.zzq.ganker.ui.adapter;

import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

/**
 * Created by zzq in 2017/7/24
 *
 * base Provider of ViewDataBinding.
 * @param <DataBinding>
 * @param <Data>
 */

public abstract class ItemBindingProvider<DataBinding extends ViewDataBinding, Data> {


    protected abstract DataBinding createBinding(ViewGroup parent);


    protected abstract void onBind(DataBinding binding, Data item);


}
