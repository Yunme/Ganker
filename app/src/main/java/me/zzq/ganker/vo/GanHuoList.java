package me.zzq.ganker.vo;

import java.util.List;

/**
 * Created by zzq in 2017/7/24
 */

public class GanHuoList {

    private List<GanHuo> Android;
    private List<GanHuo> iOS;
    private List<GanHuo> 休息视频;
    private List<GanHuo> 拓展资源;
    private List<GanHuo> 瞎推荐;
    private List<GanHuo> 福利;
    private List<GanHuo> App;
    private List<GanHuo> 前端;

    public List<GanHuo> getAndroid() {
        return Android;
    }

    public void setAndroid(List<GanHuo> android) {
        Android = android;
    }

    public List<GanHuo> getiOS() {
        return iOS;
    }

    public void setiOS(List<GanHuo> iOS) {
        this.iOS = iOS;
    }

    public List<GanHuo> get休息视频() {
        return 休息视频;
    }

    public void set休息视频(List<GanHuo> 休息视频) {
        this.休息视频 = 休息视频;
    }

    public List<GanHuo> get拓展资源() {
        return 拓展资源;
    }

    public void set拓展资源(List<GanHuo> 拓展资源) {
        this.拓展资源 = 拓展资源;
    }

    public List<GanHuo> get瞎推荐() {
        return 瞎推荐;
    }

    public void set瞎推荐(List<GanHuo> 瞎推荐) {
        this.瞎推荐 = 瞎推荐;
    }

    public List<GanHuo> get福利() {
        return 福利;
    }

    public void set福利(List<GanHuo> 福利) {
        this.福利 = 福利;
    }

    public List<GanHuo> getApp() {
        return App;
    }

    public void setApp(List<GanHuo> app) {
        App = app;
    }

    public List<GanHuo> get前端() {
        return 前端;
    }

    public void set前端(List<GanHuo> 前端) {
        this.前端 = 前端;
    }
}
