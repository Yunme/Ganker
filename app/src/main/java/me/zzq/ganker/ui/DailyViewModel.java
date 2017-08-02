package me.zzq.ganker.ui;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
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

    private final GanHuoRepository ganHuoRepository;

    private MediatorLiveData<Resource<List<GanHuo>>> ganHuoList = new MediatorLiveData<>();

    private MutableLiveData<Integer> pageLiveData = new MutableLiveData<>();

    private int page = 1;

    @Inject
    public DailyViewModel(final GanHuoRepository ganHuoRepository) {
        this.ganHuoRepository = ganHuoRepository;

        ganHuoList = (MediatorLiveData<Resource<List<GanHuo>>>) Transformations.switchMap(pageLiveData, new Function<Integer, LiveData<Resource<List<GanHuo>>>>() {

            @Override
            public LiveData<Resource<List<GanHuo>>> apply(Integer input) {
                return ganHuoRepository.loadGanHuoWelfare(input);
            }
        });
        setPage(page);
    }

    public LiveData<Resource<List<GanHuo>>> getGanHuoList() {
        return ganHuoList;
    }

    public void loadNextPage() {
        page++;
        setPage(page);
    }

    public void setPage(int page) {
        pageLiveData.setValue(page);
    }
}
