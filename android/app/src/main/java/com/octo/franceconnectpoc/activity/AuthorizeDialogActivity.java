package com.octo.franceconnectpoc.activity;

import com.octo.franceconnectpoc.BuildConfig;
import com.octo.franceconnectpoc.R;
import com.octo.franceconnectpoc.utils.FranceConnectUtils;
import com.octo.franceconnectpoc.utils.FranceConnectWebViewClient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.security.SecureRandom;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lda on 02/11/15.
 */
public class AuthorizeDialogActivity extends AppCompatActivity {

    // ----------------------------------
    // CONSTANTS
    // ----------------------------------
    public static final String RESULT_EXTRA_COOKIE = "cookie";
    public static final String RESULT_EXTRA_CODE = "code";
    public static final String RESULT_EXTRA_NONCE = "nonce";
    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    @Bind(R.id.webview)
    protected WebView mAuthorizeWebview;
    @Bind(R.id.progress_webview)
    protected ProgressBar mProgressBar;

    // ----------------------------------
    // ATTRIBUTES
    // ----------------------------------
    private String mState;
    private String mNonce;

    // ----------------------------------
    // LIFECYCLE
    // ----------------------------------
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorize_dialog);
        ButterKnife.bind(this);

        generateStateAndNonce();

        mAuthorizeWebview.getSettings().setJavaScriptEnabled(true);
        mAuthorizeWebview.setWebViewClient(new AuthorizeWebViewClient(mProgressBar));
        mAuthorizeWebview.loadUrl(FranceConnectUtils.getAuthorizeUrl(mNonce, mState));
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    private void generateStateAndNonce() {
        SecureRandom random = new SecureRandom();
        byte[] b = new byte[20];
        mState = randomString(b, random);
        mNonce = randomString(b, random);
    }

    @NonNull
    private String randomString(byte[] b, SecureRandom random) {
        random.nextBytes(b);
        return Base64.encodeToString(b, Base64.URL_SAFE | Base64.NO_PADDING | Base64.NO_WRAP);
    }

    // ----------------------------------
    // INNER CLASSES
    // ----------------------------------
    private class AuthorizeWebViewClient extends FranceConnectWebViewClient {

        public AuthorizeWebViewClient(ProgressBar progressBar) {
            super(progressBar);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains(BuildConfig.FC_CALLBACK_URL)) {

                String cookie = CookieManager.getInstance().getCookie(BuildConfig.FC_BASE_URL);
                Uri uri = Uri.parse(url);
                String code = uri.getQueryParameter("code");
                String state = uri.getQueryParameter("state");

                if (!TextUtils.isEmpty(cookie)
                        && !TextUtils.isEmpty(code)
                        && !TextUtils.isEmpty(state)
                        && state.equals(mState)) {
                    Intent data = new Intent();
                    data.putExtra(RESULT_EXTRA_COOKIE, cookie);
                    data.putExtra(RESULT_EXTRA_CODE, code);
                    data.putExtra(RESULT_EXTRA_NONCE, mNonce);
                    setResult(RESULT_OK, data);
                }
                finish();

                return true;
            } else {
                return super.shouldOverrideUrlLoading(view, url);
            }
        }
    }
}
