package me.zzq.ganker.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import me.zzq.ganker.GankerApplication;

/**
 * Created by zzq in 2017/7/17
 */

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class})
public interface AppComponent {

    void inject(GankerApplication gankerApplication);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
