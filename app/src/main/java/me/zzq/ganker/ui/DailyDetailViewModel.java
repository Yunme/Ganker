package me.zzq.ganker.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import me.zzq.ganker.repository.GanHuoRepository;
import me.zzq.ganker.vo.GanHuo;
import me.zzq.ganker.vo.Resource;

/**
 * Created by zzq in 2017/7/24
 */

public class DailyDetailViewModel extends ViewModel {

    private LiveData<Resource<List<GanHuo>>> ganHuoList;
    private GanHuoRepository ganHuoRepository;

    @Inject
    public DailyDetailViewModel(GanHuoRepository ganHuoRepository) {
       this.ganHuoRepository = ganHuoRepository;
    }

    public void setDate(String date, String type) {

        this.ganHuoList = ganHuoRepository.loadGanHuoList(date, type);
    }

    public LiveData<Resource<List<GanHuo>>> getGanHuoList() {
        return ganHuoList;
    }
}
