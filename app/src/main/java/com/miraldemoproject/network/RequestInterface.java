package com.miraldemoproject.network;


import com.miraldemoproject.uiretrofitrx.model.Rides;
import com.miraldemoproject.user.model.AndroidUser;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;


public interface RequestInterface {
    @GET("android/jsonarray/")
    Observable<List<AndroidUser>>getAndroidUsers();

    @GET("promos")
    Observable<List<Rides>> register();


}
