package me.zzq.ganker.ui;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;
import android.util.Log;

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

    private final GanHuoRepository ganHuoRepository;
    private final MutableLiveData<String> dateLiveData = new MutableLiveData<>();
    private final MediatorLiveData<List> listMediatorLiveData;
    private MediatorLiveData<Resource<List<GanHuo>>> ganHuoList = new MediatorLiveData<>();
    private LifecycleOwner lifecycleOwner;

    @Inject
    @SuppressWarnings("unchecked")
    public DailyDetailViewModel(final GanHuoRepository ganHuoRepository) {
        this.ganHuoRepository = ganHuoRepository;

        this.listMediatorLiveData = new MediatorLiveData<>();
        listMediatorLiveData.addSource(ganHuoList, new Observer<Resource<List<GanHuo>>>() {
            int i = 0;

            @Override
            public void onChanged(@Nullable Resource<List<GanHuo>> listResource) {
                Log.d("TAG", "onChanged: " + (i++));
                List resultList = new ArrayList<>();
                String type = "";
                if (listResource.data != null && listResource.data.size() > 0) {
                    type = listResource.data.get(0).getType();
                }
                resultList.add(type);
                if (listResource.status == Resource.Status.SUCCESS) {
                    resultList.addAll(listResource.data);
                    listMediatorLiveData.setValue(resultList);
                }
            }
        });
    }

    public void setDate(String date) {
        dateLiveData.setValue(date);
    }

    public void fetchGanHuo(final String date) {
        final LiveData<List<String>> typeList = ganHuoRepository.getTypesOfDailyGanHuo(date);
        ganHuoList.addSource(typeList, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> list) {
                ganHuoList.removeSource(typeList);
                for (String type : list) {
                    ganHuoList.setValue(ganHuoRepository.loadGanHuoList(date, type).getValue());
                }
            }
        });
    }

    public void setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;
    }

    public MediatorLiveData<List> getListMediatorLiveData() {
        return listMediatorLiveData;
    }
}
