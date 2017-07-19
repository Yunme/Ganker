package me.zzq.ganker.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import me.zzq.ganker.ui.MainActivity;

/**
 * Created by zzq in 2017/7/19
 */

@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeActivity();
}
