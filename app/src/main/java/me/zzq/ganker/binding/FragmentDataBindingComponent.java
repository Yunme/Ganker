package me.zzq.ganker.binding;

import android.databinding.DataBindingComponent;
import android.support.v4.app.Fragment;

/**
 * Created by zzq in 2017/7/18
 * A Data Binding Component implementation for fragments
 */

public class FragmentDataBindingComponent implements DataBindingComponent {

    private final FragmentBindingAdapters fragmentBindingAdapters;

    public FragmentDataBindingComponent(Fragment fragment) {
        this.fragmentBindingAdapters = new FragmentBindingAdapters(fragment);
    }

    @Override
    public FragmentBindingAdapters getFragmentBindingAdapters() {
        return fragmentBindingAdapters;
    }
}
