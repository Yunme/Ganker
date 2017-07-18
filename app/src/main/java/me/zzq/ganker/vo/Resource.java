package me.zzq.ganker.vo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by zzq in 2017/7/18
 * <p>
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */

public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;


    public Resource(@NonNull Status status, @Nullable String message, @Nullable T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }


    @NonNull
    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, null, data);
    }

    @NonNull
    public static <T> Resource<T> error(String message, @Nullable T data) {
        return new Resource<>(Status.ERROR, message, data);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, null, data);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) obj;
        if (status != resource.status) {
            return false;
        }

        if (message != null ? !message.equals(resource.message) : resource.message != null) {
            return false;
        }

        return data != null ? data.equals(resource.data) : resource.data != null;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * Status of a resource that is provided to the UI
     * <p>
     * These are usually created by the Repository classes where they return
     * {@code LiveData<Resource<T>>} to pass back the latest data to the UI with its fetch status.
     */
    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

}
