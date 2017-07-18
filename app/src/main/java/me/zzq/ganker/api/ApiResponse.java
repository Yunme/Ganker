package me.zzq.ganker.api;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.regex.Pattern;

import retrofit2.Response;

/**
 * Created by zzq in 2017/7/18
 * <p>
 * Common class used by API responses.
 *
 * @param <T>
 */

public class ApiResponse<T> {

    private static final Pattern LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"");


    public final int code;

    @Nullable
    public final T body;
    @Nullable
    public final String errorMessage;

    public ApiResponse(Throwable error) {
        code = 500;
        body = null;
        errorMessage = error.getMessage();
    }

    public ApiResponse(Response<T> response) {
        code = response.code();
        if (response.isSuccessful()) {
            body = response.body();
            errorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            errorMessage = message;
            body = null;
        }
    }

    public boolean isSuccessful() {
        return code >= 200 && code < 300;
    }

}
