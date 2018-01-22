package com.miraldemoproject.di.components;

import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.miraldemoproject.di.modules.ApplicationModule;
import com.miraldemoproject.di.modules.TextRecognitionModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApplicationModule.class, TextRecognitionModule.class})
public interface ApplicationComponent {

    Retrofit exposeRetrofit();

    VisionServiceRestClient exposeVisionServiceRestClient();
}
