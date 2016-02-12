package com.octo.franceconnectpoc.fragment;

import com.octo.franceconnectpoc.utils.FranceConnectWebViewClient;
import com.octo.franceconnectpoc.R;
import com.octo.franceconnectpoc.utils.FranceConnectUtils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lda on 17/11/2015.
 */
public class HistoryFragment extends Fragment {

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private Bundle mWebViewState;

    @Bind(R.id.webview)
    protected WebView mAuthorizeWebview;
    @Bind(R.id.progress_webview)
    protected ProgressBar mProgressBar;

    // ----------------------------------
    // LIFECYCLE
    // ----------------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mAuthorizeWebview.getSettings().setJavaScriptEnabled(true);
        mAuthorizeWebview.setWebViewClient(new FranceConnectWebViewClient(mProgressBar));

        if (mWebViewState != null) {
            mAuthorizeWebview.restoreState(mWebViewState);
        } else {
            mAuthorizeWebview.loadUrl(FranceConnectUtils.getTracesUrl());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (mWebViewState == null) {
            mWebViewState = new Bundle();
        }

        mAuthorizeWebview.saveState(mWebViewState);
    }

}
