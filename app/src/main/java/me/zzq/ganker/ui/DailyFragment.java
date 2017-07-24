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
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import me.zzq.ganker.R;
import me.zzq.ganker.binding.FragmentDataBindingComponent;
import me.zzq.ganker.databinding.FragmentDailyBinding;
import me.zzq.ganker.di.Injectable;
import me.zzq.ganker.ui.adapter.DailyImageProvider;
import me.zzq.ganker.ui.adapter.DataBoundListAdapter;
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

    DataBoundListAdapter dataBoundListAdapter;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<FragmentDailyBinding> dataBinding;

    private DailyViewModel dailyViewModel;

    private DailyDetailFragment dailyDetailFragment;

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
        dailyDetailFragment = new DailyDetailFragment();
        dataBoundListAdapter = new DataBoundListAdapter();

        OnItemClickListener onItemClickListener = new OnItemClickListener<GanHuo>() {
            @Override
            public void onItemClick(View view, int position, GanHuo item) {
                Bundle bundle = new Bundle();
                bundle.putString("transitionName", "transition" + position);
                bundle.putSerializable("ganHuo", item);
                dailyDetailFragment.setArguments(bundle);

                DailyFragment.this.setSharedElementReturnTransition(TransitionInflater.from(DailyFragment.this.getContext()).inflateTransition(R.transition.default_transition));
                DailyFragment.this.setExitTransition(TransitionInflater.from(DailyFragment.this.getContext()).inflateTransition(android.R.transition.no_transition));
                DailyFragment.this.setEnterTransition(TransitionInflater.from(DailyFragment.this.getContext()).inflateTransition(android.R.transition.no_transition));

                dailyDetailFragment.setSharedElementEnterTransition(TransitionInflater.from(DailyFragment.this.getContext()).inflateTransition(R.transition.default_transition));
                dailyDetailFragment.setEnterTransition(TransitionInflater.from(DailyFragment.this.getContext()).inflateTransition(android.R.transition.no_transition));
                dailyDetailFragment.setExitTransition(TransitionInflater.from(DailyFragment.this.getContext()).inflateTransition(android.R.transition.no_transition));
                getFragmentManager().beginTransaction().replace(R.id.container, dailyDetailFragment)
                        .addToBackStack(null)
                        .addSharedElement(view, "transition" + position)
                        .commit();
            }
        };

        dataBoundListAdapter.viewDataBinding.put(GanHuo.class, new DailyImageProvider(dataBindingComponent, onItemClickListener));

        initRecyclerView();
    }

    private void initRecyclerView() {
        dataBinding.get().recyclerView.setAdapter(dataBoundListAdapter);
        new LinearSnapHelper().attachToRecyclerView(dataBinding.get().recyclerView);
        ((LinearLayoutManager) dataBinding.get().recyclerView.getLayoutManager()).setOrientation(LinearLayoutManager.HORIZONTAL);
        dailyViewModel.getGanHuoList().observe(this, new Observer<Resource<List<GanHuo>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<GanHuo>> listResource) {
                if (listResource == null) {
                    dataBoundListAdapter.setItems(null);
                } else {
                    dataBoundListAdapter.setItems(listResource.data);
                }
            }
        });
    }
}
