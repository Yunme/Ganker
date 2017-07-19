package me.zzq.ganker.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import me.zzq.ganker.R;
import me.zzq.ganker.binding.FragmentDataBindingComponent;
import me.zzq.ganker.databinding.FragmentDailyBinding;
import me.zzq.ganker.di.Injectable;
import me.zzq.ganker.ui.adapter.DailyImageAdapter;
import me.zzq.ganker.ui.adapter.OnItemClickListener;
import me.zzq.ganker.util.AutoClearedValue;
import me.zzq.ganker.vo.GanHuo;
import me.zzq.ganker.vo.Resource;

/**
 * Created by zzq in 2017/7/17
 */

public class DailyFragment extends LifecycleFragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    DailyImageAdapter dailyImageAdapter;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<FragmentDailyBinding> dataBinding;

    private DailyViewModel dailyViewModel;

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
        dailyViewModel = ViewModelProviders.of(this, viewModelFactory).get(DailyViewModel.class);
        dailyImageAdapter = new DailyImageAdapter(dataBindingComponent, new OnItemClickListener<GanHuo>() {
            @Override
            public void onItemClick(int position, GanHuo item) {

            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        dataBinding.get().recyclerView.setAdapter(dailyImageAdapter);
        new LinearSnapHelper().attachToRecyclerView(dataBinding.get().recyclerView);
        ((LinearLayoutManager) dataBinding.get().recyclerView.getLayoutManager()).setOrientation(LinearLayoutManager.HORIZONTAL);
        dailyViewModel.getGanHuoList().observe(this, new Observer<Resource<List<GanHuo>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<GanHuo>> listResource) {
                if (listResource == null) {
                    dailyImageAdapter.replace(null);
                } else {
                    dailyImageAdapter.replace(listResource.data);
                }
            }
        });
    }
}
