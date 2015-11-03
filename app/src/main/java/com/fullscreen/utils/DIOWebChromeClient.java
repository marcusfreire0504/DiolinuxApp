package com.fullscreen.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;

import com.example.webview.R;

/**
 * Created by jackson on 02/11/15.
 */
public class DIOWebChromeClient extends WebChromeClient {

    private final WebView webView;
    private final FrameLayout frameLayoutContainer;
    private final Context context;
    private View viewVideoProgress;
    private View viewCustom;
    private CustomViewCallback customViewCallback;

    public DIOWebChromeClient(Context context, WebView webView, FrameLayout frameLayoutContainer) {
        this.context = context;
        this.webView = webView;
        this.frameLayoutContainer = frameLayoutContainer;
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (viewCustom == null) {
            viewCustom = view;
            webView.setVisibility(View.GONE);
            frameLayoutContainer.setVisibility(View.VISIBLE);
            frameLayoutContainer.addView(view);
            customViewCallback = callback;
        } else {
            callback.onCustomViewHidden();
        }
    }

    @Override
    public View getVideoLoadingProgressView() {
        if (viewVideoProgress == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            viewVideoProgress = inflater.inflate(R.layout.video_progress, null);
        }
        return viewVideoProgress;
    }

    @Override
    public void onHideCustomView() {
        super.onHideCustomView();
        if (viewCustom != null) {
            setWebViewVisible();
            removeCustomViewFromParent();
        }
    }

    private void setWebViewVisible() {
        webView.setVisibility(View.VISIBLE);
        frameLayoutContainer.setVisibility(View.GONE);
        viewCustom.setVisibility(View.GONE);
    }

    private void removeCustomViewFromParent() {
        frameLayoutContainer.removeView(viewCustom);
        customViewCallback.onCustomViewHidden();
        viewCustom = null;
    }

    public boolean hideCustomView() {
        if (viewCustom == null) {
            return false;
        } else {
            onHideCustomView();
            return true;
        }
    }
}
