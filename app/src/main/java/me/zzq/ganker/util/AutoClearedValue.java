package me.zzq.ganker.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by zzq in 2017/7/18
 * <p>
 * A value holder that automatically clears the reference if the Fragment's view is destroyed.
 *
 * @param <T>
 */

public class AutoClearedValue<T> {

    private T value;

    public AutoClearedValue(final Fragment fragment, T value) {
        final FragmentManager fragmentManager = fragment.getFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                AutoClearedValue.this.value = null;
                fragmentManager.unregisterFragmentLifecycleCallbacks(this);
            }
        }, false);

        this.value = value;
    }

    public T get() {
        return value;
    }
}
