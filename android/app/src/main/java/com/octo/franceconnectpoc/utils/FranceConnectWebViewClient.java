package com.octo.franceconnectpoc.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by lda on 17/11/2015.
 */
public class FranceConnectWebViewClient extends WebViewClient {

    private ProgressBar mProgressBar;

    public FranceConnectWebViewClient(final ProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        mProgressBar.setAlpha(0);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.animate().alpha(1).setListener(null);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        mProgressBar.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }
}
