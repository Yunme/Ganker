package me.zzq.ganker.repository;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.zzq.ganker.api.GankerService;
import me.zzq.ganker.db.GanHuoDao;
import me.zzq.ganker.db.GankerDb;
import me.zzq.ganker.util.AppExecutors;

/**
 * Created by zzq in 2017/7/18
 */

@Singleton
public class GanHuoRepository {

    private final GankerDb gankerDb;

    private final GanHuoDao ganHuoDao;

    private final GankerService gankerService;

    private final AppExecutors appExecutors;


    @Inject
    public GanHuoRepository(GankerDb gankerDb, GanHuoDao ganHuoDao, GankerService gankerService, AppExecutors appExecutors) {
        this.gankerDb = gankerDb;
        this.ganHuoDao = ganHuoDao;
        this.gankerService = gankerService;
        this.appExecutors = appExecutors;
    }


}
