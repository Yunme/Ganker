package me.zzq.ganker.ui;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import me.zzq.ganker.R;
import me.zzq.ganker.databinding.ActivityWebBinding;
import me.zzq.ganker.util.AutoClearedValue;
import me.zzq.ganker.vo.GanHuo;

/**
 * Created by zzq in 2017/8/1
 */

public class WebViewActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    public static final String WEB_URL = "web_url";
    public static final String GAN_HUO = "gan_huo";

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private AutoClearedValue<ActivityWebBinding> autoClearedBinding;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWebBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_web);
        autoClearedBinding = new AutoClearedValue<>(this, binding);
        url = getIntent().getStringExtra(WEB_URL);
        GanHuo ganHuo = (GanHuo) getIntent().getSerializableExtra(GAN_HUO);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.setTitle(ganHuo.getDesc());
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initWebView(binding.webView);
        binding.webView.loadUrl(url);
    }

    private void initWebView(WebView webView) {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);

        webView.setWebViewClient(new MyWebViewClient());
        webView.setWebChromeClient(new MyWebChromeClient());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_web_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_open_in_new:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (autoClearedBinding.get().webView.canGoBack()) {
            autoClearedBinding.get().webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            autoClearedBinding.get().progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                autoClearedBinding.get().setLoadingComplete(true);
            }
        }
    }
}
