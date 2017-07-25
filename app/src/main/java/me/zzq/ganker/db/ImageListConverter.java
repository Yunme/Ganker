package me.zzq.ganker.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by zzq in 2017/7/25
 */


public class ImageListConverter {

    @TypeConverter
    public String convertListToJson(List<String> images) {
        Gson gson = new Gson();
        return gson.toJson(images);
    }


    @TypeConverter
    public List<String> convertJsonToList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<String>>(){}.getType());

    }

}
