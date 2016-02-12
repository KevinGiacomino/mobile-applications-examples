package com.octo.franceconnectpoc.utils;

import com.octo.franceconnectpoc.BuildConfig;

import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by lda on 03/11/15.
 */
public final class FranceConnectUtils {

    private FranceConnectUtils() {
    }

    // ----------------------------------
    // PRIVATE METHODS
    // ----------------------------------
    @NonNull
    public static String getAuthorizeUrl(String nonce, String state) {
        Uri.Builder builder = Uri.parse(BuildConfig.FC_BASE_URL).buildUpon();

        builder.appendEncodedPath(BuildConfig.FC_AUTHORIZE_PATH);
        builder.appendQueryParameter("response_type", "code");
        builder.appendQueryParameter("client_id", BuildConfig.FC_CLIENT_ID);
        builder.appendQueryParameter("redirect_uri", BuildConfig.FC_CALLBACK_URL);
        builder.appendQueryParameter("scope", BuildConfig.FC_SCOPES);
        builder.appendQueryParameter("state", state);
        builder.appendQueryParameter("nonce", nonce);

        return builder.build().toString();
    }

    @NonNull
    public static String getTracesUrl() {
        Uri.Builder builder = Uri.parse(BuildConfig.FC_BASE_URL).buildUpon();
        builder.appendEncodedPath(BuildConfig.FC_TRACES_PATH);
        return builder.build().toString();
    }

}
