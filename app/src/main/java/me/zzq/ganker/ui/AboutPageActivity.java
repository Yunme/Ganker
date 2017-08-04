package me.zzq.ganker.ui;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import me.drakeet.multitype.Items;
import me.drakeet.support.about.AbsAboutActivity;
import me.drakeet.support.about.Card;
import me.drakeet.support.about.Category;
import me.drakeet.support.about.Contributor;
import me.drakeet.support.about.License;
import me.drakeet.support.about.Line;
import me.zzq.ganker.BuildConfig;
import me.zzq.ganker.R;

/**
 * Created by zzq in 2017/8/4
 */

public class AboutPageActivity extends AbsAboutActivity implements LifecycleRegistryOwner {

    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreateHeader(ImageView icon, TextView slogan, TextView version) {
        setNavigationIcon(R.drawable.ic_toolbar_close);
        icon.setImageResource(R.mipmap.ic_launcher_round);
        slogan.setText("Ganker");
        version.setText("v" + BuildConfig.VERSION_NAME);
    }

    @Override
    protected void onItemsCreated(@NonNull Items items) {
        items.add(new Category("介绍与帮助"));
        items.add(new Card("Ganker", "Share"));

        items.add(new Line());

        items.add(new Category("Developer"));
        items.add(new Contributor(R.drawable.ic_toolbar_info, "RyanChang", "Developer", "https://github.com/Yunme"));

        items.add(new Line());

        items.add(new Category("Open Source Licenses"));
        items.add(new License("Android Open Source Project", "The Android Open Source Project, Inc.", License.APACHE_2, "https://source.android.com/"));
        items.add(new License("Android Architecture Components samples", "The Android Open Source Project, Inc.", License.APACHE_2, "https://github.com/googlesamples/android-architecture-components"));
        items.add(new License("Retrofit", "Square, Inc.", License.APACHE_2, "https://github.com/square/retrofit"));
        items.add(new License("Glide", "bumptech", "Berkly Software Distribution", "https://github.com/bumptech/glide"));
        items.add(new License("Dagger 2", "Google, Inc.", License.APACHE_2, "https://github.com/google/dagger"));
        items.add(new License("MultiType", "drakeet", License.APACHE_2, "https://github.com/drakeet/MultiType"));
        items.add(new License("about-page", "drakeet", License.APACHE_2, "https://github.com/drakeet/about-page"));
        items.add(new License("LeakCanary", "Square, Inc.", License.APACHE_2, "https://github.com/square/leakcanary"));
    }


    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
}
