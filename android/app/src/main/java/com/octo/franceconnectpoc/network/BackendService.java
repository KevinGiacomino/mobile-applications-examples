package com.octo.franceconnectpoc.network;

import com.octo.franceconnectpoc.model.UserInfo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Query;

/**
 * Created by lda on 02/11/15.
 */
public interface BackendService {

    @HEAD("/")
    Call<Void> wakeUp();

    @GET("/userinfo")
    Call<UserInfo> userInfo(@Query("code") String code, @Query("nonce") String nonce);
}
