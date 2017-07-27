package me.zzq.ganker.api;

import java.util.List;

/**
 * Created by zzq in 2017/7/19
 * <p>
 * To wrap Gank.io API
 */

public class HttpResult<T> {

    public boolean error;

    public T results;

    public List<String> category;

}
