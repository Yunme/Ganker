package me.zzq.ganker.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

import me.zzq.ganker.api.ApiResponse;
import me.zzq.ganker.api.GankerService;
import me.zzq.ganker.api.HttpResult;
import me.zzq.ganker.db.GanHuoDao;
import me.zzq.ganker.db.GankerDb;
import me.zzq.ganker.util.AppExecutors;
import me.zzq.ganker.vo.GanHuo;
import me.zzq.ganker.vo.GanHuoList;
import me.zzq.ganker.vo.Resource;

/**
 * Created by zzq in 2017/7/18
 * <p>
 * Repository that handles GanHuo instances.
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


    public LiveData<Resource<List<GanHuo>>> loadGanHuoWelfare(final int page) {
        return new NetworkBoundResource<List<GanHuo>, HttpResult<List<GanHuo>>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull HttpResult<List<GanHuo>> item) {
                for (GanHuo ganHuo : item.results) {
                    int start = ganHuo.getContent().indexOf("src=\"") + 5;
                    int end = ganHuo.getContent().indexOf(".jpg") + 4;
                    ganHuo.setUrl(ganHuo.getContent().substring(start, end));
                    ganHuo.setPublishedAt(ganHuo.getPublishedAt().substring(0, 10));
                    if (ganHuo.getTitle().startsWith("今日力推：")) {
                        ganHuo.setTitle(ganHuo.getTitle().substring(5));
                    }
                }
                ganHuoDao.insert(item.results);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<GanHuo> data) {
                if (data == null || data.isEmpty()) {
                    return true;
                }
                if (data.size() > 0) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-DD", Locale.CHINA);
                    try {
                        Date date = simpleDateFormat.parse(data.get(0).getPublishedAt());
                        return !DateUtils.isToday(date.getTime());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                return false;
            }

            @NonNull
            @Override
            protected LiveData<List<GanHuo>> loadFromDb() {
                return ganHuoDao.loadGanHuoWelfareList(page);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<HttpResult<List<GanHuo>>>> createCall() {
                return gankerService.getGanHuoWelfare(10, page);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<GanHuo>>> loadGanHuoList(final String date, final String type) {
        return new NetworkBoundResource<List<GanHuo>, HttpResult<GanHuoList>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull HttpResult<GanHuoList> item) {
                insertCheckNotNull(ganHuoDao, item.results.getAndroid());
                insertCheckNotNull(ganHuoDao, item.results.getApp());
                insertCheckNotNull(ganHuoDao, item.results.getiOS());
                insertCheckNotNull(ganHuoDao, item.results.get休息视频());
                insertCheckNotNull(ganHuoDao, item.results.get前端());
                insertCheckNotNull(ganHuoDao, item.results.get拓展资源());
                insertCheckNotNull(ganHuoDao, item.results.get瞎推荐());
                insertCheckNotNull(ganHuoDao, item.results.get福利());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<GanHuo> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<GanHuo>> loadFromDb() {
                return ganHuoDao.loadGanHuoList(date, type);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<HttpResult<GanHuoList>>> createCall() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                String formatted = "";
                try {
                    Date date1 = simpleDateFormat.parse(date);
                    simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
                    formatted = simpleDateFormat.format(date1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return gankerService.getGanHuoList(formatted);
            }
        }.asLiveData();
    }


    public LiveData<List<String>> getTypesOfDailyGanHuo(String date) {
        return ganHuoDao.loadGanHuoDailyType(date);
    }

    private void insertCheckNotNull(GanHuoDao dao, List<GanHuo> list) {
        if (list != null) {
            for (GanHuo ganHuo : list) {
                ganHuo.setPublishedAt(ganHuo.getPublishedAt().substring(0, 10));
            }
            dao.insert(list);
        }
    }
}
