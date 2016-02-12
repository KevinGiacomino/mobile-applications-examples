package com.octo.franceconnectpoc.network;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by lda on 02/11/15.
 */
public interface FranceConnectService {

    @GET("/api/v1/logout?force=true")
    Call<Void> logOut();
}
