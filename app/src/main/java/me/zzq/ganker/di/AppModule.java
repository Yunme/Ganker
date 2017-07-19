package me.zzq.ganker.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.zzq.ganker.api.GankerService;
import me.zzq.ganker.api.LiveDataCallAdapterFactory;
import me.zzq.ganker.db.GanHuoDao;
import me.zzq.ganker.db.GankerDb;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zzq in 2017/7/17
 */

@Module(includes = ViewModelModule.class)
public class AppModule {

    @Singleton
    @Provides
    GankerService provideGankerService() {
        return new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(GankerService.class);
    }

    @Singleton
    @Provides
    GankerDb provideGankerDb(Application application) {
        return Room.databaseBuilder(application, GankerDb.class, "ganker.db").build();
    }

    @Singleton
    @Provides
    GanHuoDao provideGanHuoDao(GankerDb gankerDb) {
        return gankerDb.ganHuoDao();
    }

}
