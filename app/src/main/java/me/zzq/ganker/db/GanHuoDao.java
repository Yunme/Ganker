package me.zzq.ganker.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

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
    public abstract void insert(@NonNull List<GanHuo> ganHuoList);

    @Query("SELECT * FROM GanHuo WHERE type is NULL ORDER BY publishedAt DESC LIMIT 10 OFFSET (:page - 1) * 10")
    public abstract LiveData<List<GanHuo>> loadGanHuoWelfareList(int page);

    @Query("SELECT * FROM GanHuo WHERE publishedAt = :date AND type = :type")
    public abstract LiveData<List<GanHuo>> loadGanHuoList(String date, String type);

    @Query("SELECT DISTINCT type FROM GanHuo WHERE publishedAt = :date AND type != '福利'")
    public abstract LiveData<List<String>> loadGanHuoDailyType(String date);
}
