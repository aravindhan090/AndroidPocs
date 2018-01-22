package com.miraldemoproject.di.modules;

import com.miraldemoproject.network.RequestInterface;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Retrofit;

@Module
public class UserModule {

    @Provides
    RequestInterface getRequestInterface(Retrofit retrofit) {
        return retrofit.create(RequestInterface.class);
    }

    @Provides
    CompositeDisposable getCompositeDisposable() {
        return new CompositeDisposable();
    }
}
