package me.zzq.ganker.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import me.zzq.ganker.vo.GanHuo;

/**
 * Created by zzq in 2017/7/18
 * <p>
 * The DAO of GanHuo.
 * Interface for database access on GanHuo related operations.
 */

@Dao
public abstract class GanHuoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<GanHuo> ganHuoList);

    @Query("SELECT * FROM GanHuo")
    public abstract LiveData<List<GanHuo>> loadGanHuoList();
}
