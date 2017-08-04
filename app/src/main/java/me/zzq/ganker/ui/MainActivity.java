package me.zzq.ganker.ui;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import me.zzq.ganker.R;

/**
 * Created by zzq in 2017/7/17
 */

public class MainActivity extends AppCompatActivity implements LifecycleRegistryOwner, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            DailyFragment dailyFragment = new DailyFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, dailyFragment)
                    .addToBackStack(null).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_about_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_info:
                Intent intent = new Intent(this, AboutPageActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            finish();
        }
    }
}
