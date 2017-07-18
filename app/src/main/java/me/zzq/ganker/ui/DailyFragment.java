package me.zzq.ganker.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import me.zzq.ganker.R;
import me.zzq.ganker.binding.FragmentDataBindingComponent;
import me.zzq.ganker.databinding.FragmentDailyBinding;
import me.zzq.ganker.di.Injectable;
import me.zzq.ganker.ui.adapter.DailyImageAdapter;
import me.zzq.ganker.util.AutoClearedValue;

/**
 * Created by zzq in 2017/7/17
 */

public class DailyFragment extends LifecycleFragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    DailyImageAdapter dailyImageAdapter;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<FragmentDailyBinding> dataBinding;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentDailyBinding dailyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily, container,
                false, dataBindingComponent);
        this.dataBinding = new AutoClearedValue<>(this, dailyBinding);
        return dailyBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecyclerView();
    }

    private void initRecyclerView() {
        dataBinding.get().recyclerView.setAdapter(dailyImageAdapter);
    }
}
