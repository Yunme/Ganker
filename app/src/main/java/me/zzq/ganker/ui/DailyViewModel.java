package me.zzq.ganker.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import me.zzq.ganker.repository.GanHuoRepository;
import me.zzq.ganker.vo.GanHuo;
import me.zzq.ganker.vo.Resource;

/**
 * Created by zzq in 2017/7/18
 */

public class DailyViewModel extends ViewModel {

    private LiveData<Resource<List<GanHuo>>> ganHuoList;

    @Inject
    public DailyViewModel(GanHuoRepository ganHuoRepository) {

        ganHuoList = ganHuoRepository.loadGanHuoHeader();
    }

    public LiveData<Resource<List<GanHuo>>> getGanHuoList() {
        return ganHuoList;
    }
}
