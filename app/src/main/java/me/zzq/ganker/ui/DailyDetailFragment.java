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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import javax.inject.Inject;

import me.zzq.ganker.R;
import me.zzq.ganker.binding.FragmentDataBindingComponent;
import me.zzq.ganker.databinding.FragmentDailyDetailBinding;
import me.zzq.ganker.di.Injectable;
import me.zzq.ganker.util.AutoClearedValue;
import me.zzq.ganker.vo.GanHuo;

/**
 * Created by zzq in 2017/7/21
 */

public class DailyDetailFragment extends LifecycleFragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);

    AutoClearedValue<FragmentDailyDetailBinding> dataBinding;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentDailyDetailBinding dailyDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_detail,
                container, false, dataBindingComponent);
        Bundle bundle = getArguments();
        String transitionName = bundle.getString("transitionName");
        dailyDetailBinding.imageView.setTransitionName(transitionName);
        dailyDetailBinding.setGanHuo((GanHuo) bundle.getSerializable("ganhuo"));
        this.dataBinding = new AutoClearedValue<>(this, dailyDetailBinding);
        return dailyDetailBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        Glide.with(this)
                .load("http://pic129.nipic.com/file/20170511/7138165_193428247000_2.jpg")
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
