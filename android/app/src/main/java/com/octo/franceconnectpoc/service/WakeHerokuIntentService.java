package com.octo.franceconnectpoc.service;

import com.octo.franceconnectpoc.BuildConfig;
import com.octo.franceconnectpoc.network.BackendService;

import android.app.IntentService;
import android.content.Intent;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lda on 03/11/15.
 */
public class WakeHerokuIntentService extends IntentService {

    public WakeHerokuIntentService() {
        super("WakeHerokuIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BACKEND_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        BackendService backendService = retrofit.create(BackendService.class);
        Call call = backendService.wakeUp();

        try {
            call.execute();
        } catch (IOException e) {
            // Do nothing
        }
    }
}
