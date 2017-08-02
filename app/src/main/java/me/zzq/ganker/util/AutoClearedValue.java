package me.zzq.ganker.util;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.OnLifecycleEvent;

/**
 * Created by zzq in 2017/7/18
 * <p>
 * A value holder that automatically clears the reference if the Fragment's view is destroyed.
 *
 * @param <T>
 */

public class AutoClearedValue<T> {

    private T value;

    public AutoClearedValue(final LifecycleRegistryOwner lifecycleRegistryOwner, T value) {
        lifecycleRegistryOwner.getLifecycle().addObserver(new LifecycleObserver() {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            public void onDestroy() {
                AutoClearedValue.this.value = null;
                lifecycleRegistryOwner.getLifecycle().removeObserver(this);
            }
        });
        this.value = value;
    }

    public T get() {
        return value;
    }
}
