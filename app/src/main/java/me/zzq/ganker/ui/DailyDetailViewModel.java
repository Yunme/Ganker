package me.zzq.ganker.ui;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.zzq.ganker.repository.GanHuoRepository;
import me.zzq.ganker.vo.GanHuo;
import me.zzq.ganker.vo.Resource;

/**
 * Created by zzq in 2017/7/24
 */

public class DailyDetailViewModel extends ViewModel {

    private final String TAG = this.getClass().getSimpleName();
    private final GanHuoRepository ganHuoRepository;
    private final MutableLiveData<String> dateLiveData = new MutableLiveData<>();
    private final LiveData<List<String>> typeList;
    private final MediatorLiveData<List> resultListLiveData = new MediatorLiveData<>();
    private MediatorLiveData<Resource<List<GanHuo>>> ganHuoList = new MediatorLiveData<>();

    @Inject
    @SuppressWarnings("unchecked")
    public DailyDetailViewModel(final GanHuoRepository ganHuoRepository) {
        this.ganHuoRepository = ganHuoRepository;

        this.typeList = Transformations.switchMap(dateLiveData, new Function<String, LiveData<List<String>>>() {
            @Override
            public LiveData<List<String>> apply(String date) {
                return ganHuoRepository.getTypesOfDailyGanHuo(date);
            }
        });

        this.ganHuoList.addSource(typeList, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> list) {
                if (list == null || list.isEmpty()) {
                    // TODO: 2017/7/27 cannot load detail data when first load.
                    ganHuoList.addSource(ganHuoRepository.loadGanHuoList(dateLiveData.getValue(), null),
                            new Observer<Resource<List<GanHuo>>>() {
                                @Override
                                public void onChanged(@Nullable Resource<List<GanHuo>> listResource) {
                                    //ganHuoList.removeSource(ganHuoRepository.loadGanHuoList(dateLiveData.getValue(), null));
                                    //ganHuoList.setValue(listResource);
                                    if (listResource.status == Resource.Status.SUCCESS &&
                                            listResource.data != null && listResource.data.size() > 0)
                                        dateLiveData.setValue("2017-07-27");
                                }
                            });
                    return;
                }
                ganHuoList.removeSource(typeList);
                for (final String type : list) {
                    ganHuoList.addSource(ganHuoRepository.loadGanHuoList(dateLiveData.getValue(), type),
                            new Observer<Resource<List<GanHuo>>>() {
                                @Override
                                public void onChanged(@Nullable Resource<List<GanHuo>> listResource) {
                                    ganHuoList.removeSource(ganHuoRepository.loadGanHuoList(dateLiveData.getValue(), type));
                                    ganHuoList.setValue(listResource);
                                }
                            });
                }
            }
        });

        this.resultListLiveData.addSource(ganHuoList, new Observer<Resource<List<GanHuo>>>() {

            @Override
            public void onChanged(@Nullable Resource<List<GanHuo>> listResource) {
                List resultList = new ArrayList<>();
                if (listResource.status == Resource.Status.SUCCESS &&
                        listResource.data != null && listResource.data.size() > 0) {
                    String type = listResource.data.get(0).getType();
                    resultList.add(type);
                    resultList.addAll(listResource.data);
                    resultListLiveData.setValue(resultList);
                }
            }
        });
    }

    public void setDate(String date) {
        dateLiveData.setValue(date);
    }

    public MediatorLiveData<List> getResultListLiveData() {
        return resultListLiveData;
    }
}
