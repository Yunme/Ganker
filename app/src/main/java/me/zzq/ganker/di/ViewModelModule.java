package me.zzq.ganker.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import me.zzq.ganker.ui.DailyDetailViewModel;
import me.zzq.ganker.ui.DailyViewModel;
import me.zzq.ganker.viewmodel.GankerViewModelFactory;

/**
 * Created by zzq in 2017/7/17
 */

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DailyViewModel.class)
    abstract ViewModel bindDailyViewModel(DailyViewModel dailyViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(GankerViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(DailyDetailViewModel.class)
    abstract ViewModel bindDailyDetailViewModel(DailyDetailViewModel dailyDetailViewModel);
}
