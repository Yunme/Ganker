package me.zzq.ganker.api;

import android.arch.lifecycle.LiveData;

import java.util.List;

import me.zzq.ganker.vo.GanHuo;
import me.zzq.ganker.vo.GanHuoList;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zzq in 2017/7/18
 * <p>
 * Api service that managed by Retrofit2.
 */

public interface GankerService {

    @GET("history/content/{count}/{page}")
    LiveData<ApiResponse<HttpResult<List<GanHuo>>>> getGanHuoWelfare(@Path("count") int count, @Path("page") int page);


    @GET("day/{date}")
    LiveData<ApiResponse<HttpResult<GanHuoList>>> getGanHuoList(@Path("date") String date);

}
