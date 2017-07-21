package me.zzq.ganker.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.zzq.ganker.ui.DailyDetailFragment;
import me.zzq.ganker.ui.DailyFragment;

/**
 * Created by zzq in 2017/7/19
 */

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract DailyFragment contributeDailyFragment();

    @ContributesAndroidInjector
    abstract DailyDetailFragment contributeDailyDetailFragment();
}
