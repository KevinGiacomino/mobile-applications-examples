package com.octo.franceconnectpoc.network;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lda on 04/11/15.
 */
public class InjectCookieInterceptor implements Interceptor {

    private final String mCookie;

    public InjectCookieInterceptor(String cookie) {
        this.mCookie = cookie;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request cookieInjectedRequest = originalRequest.newBuilder()
                .addHeader("Cookie", mCookie)
                .build();

        return chain.proceed(cookieInjectedRequest);
    }
}
