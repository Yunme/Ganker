package me.zzq.ganker.ui;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import java.util.List;

import javax.inject.Inject;

import me.zzq.ganker.repository.GanHuoRepository;
import me.zzq.ganker.vo.GanHuo;
import me.zzq.ganker.vo.LoadMoreState;
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


    static class NextPageHandler implements Observer<Resource<List<GanHuo>>> {

        private final MutableLiveData<LoadMoreState> loadMoreState = new MutableLiveData<>();
        private final GanHuoRepository ganHuoRepository;
        private LiveData<Resource<List<GanHuo>>> nextPageData;
        private int page;
        private boolean hasMore;

        NextPageHandler(GanHuoRepository ganHuoRepository) {
            this.ganHuoRepository = ganHuoRepository;
        }

        void loadNextPage(int page) {
            this.page = page;
            nextPageData = ganHuoRepository.loadGanHuoWelfare(page);
            loadMoreState.setValue(new LoadMoreState(true, null));
            nextPageData.observeForever(this);

        }

        @Override
        public void onChanged(@Nullable Resource<List<GanHuo>> resultData) {
            if (resultData == null) {
                reset();
            } else {
                switch (resultData.status) {
                    case SUCCESS:
                        hasMore = true;
                        break;
                    case ERROR:
                        break;
                }
            }
        }

        private void unregister() {
            if (nextPageData != null) {
                nextPageData.removeObserver(this);
                nextPageData = null;
                if (hasMore) {
                    page = 0;
                }
            }
        }

        private void reset() {
            unregister();
            hasMore = true;
            loadMoreState.setValue(new LoadMoreState(false, null));
        }

        public MutableLiveData<LoadMoreState> getLoadMoreState() {
            return loadMoreState;
        }
    }

}
