package me.zzq.ganker.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import me.zzq.ganker.vo.GanHuo;

/**
 * Created by zzq in 2017/7/18
 * <p>
 * Main database in whole app.
 */

@Database(entities = {GanHuo.class}, version = 1)
public abstract class GankerDb extends RoomDatabase {

    abstract public GanHuoDao ganHuoDao();
}
