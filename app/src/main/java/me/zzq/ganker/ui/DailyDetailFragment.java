package me.zzq.ganker.ui;

import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import javax.inject.Inject;

import me.zzq.ganker.R;
import me.zzq.ganker.binding.FragmentDataBindingComponent;
import me.zzq.ganker.databinding.FragmentDailyDetailOpBinding;
import me.zzq.ganker.di.Injectable;
import me.zzq.ganker.ui.adapter.DailyGanHuoProvider;
import me.zzq.ganker.ui.adapter.DailyTitleProvider;
import me.zzq.ganker.ui.adapter.DataBoundListAdapter;
import me.zzq.ganker.util.AutoClearedValue;
import me.zzq.ganker.vo.GanHuo;

/**
 * Created by zzq in 2017/7/21
 */

public class DailyDetailFragment extends LifecycleFragment implements Injectable {

    public static final String GAN_HUO = "ganHuo";
    public static final String TRANSITION_NAME = "transition_name";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<FragmentDailyDetailOpBinding> dataBinding;

    DataBoundListAdapter dataBoundListAdapter;

    private DailyDetailViewModel dailyDetailViewModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentDailyDetailOpBinding dailyDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_detail_op,
                container, false, dataBindingComponent);
        this.dataBinding = new AutoClearedValue<>(this, dailyDetailBinding);
        return dailyDetailBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).setSupportActionBar(dataBinding.get().toolBar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dailyDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(DailyDetailViewModel.class);
        dataBoundListAdapter = new DataBoundListAdapter();
        dataBoundListAdapter.viewDataBinding.put(GanHuo.class, new DailyGanHuoProvider(dataBindingComponent));
        dataBoundListAdapter.viewDataBinding.put(String.class, new DailyTitleProvider());
        Bundle bundle = getArguments();
        String transitionName = bundle.getString(TRANSITION_NAME);
        dataBinding.get().imageView.setTransitionName(transitionName);
        GanHuo ganHuo = (GanHuo) bundle.getSerializable(GAN_HUO);
        dataBinding.get().setGanHuo(ganHuo);
        dataBinding.get().recyclerView.setAdapter(dataBoundListAdapter);
        dailyDetailViewModel.setDate(ganHuo.getPublishedAt());
        dailyDetailViewModel.getResultListLiveData().observe(this, new Observer<List>() {
            @Override
            public void onChanged(@Nullable List list) {
                dataBoundListAdapter.addItems(list);
            }
        });

        dataBinding.get().toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        Glide.with(this)
                .load(ganHuo.getUrl())
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(dataBinding.get().imageView);
    }
}
