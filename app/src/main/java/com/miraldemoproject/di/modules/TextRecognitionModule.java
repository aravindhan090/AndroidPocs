package com.miraldemoproject.di.modules;

import com.microsoft.projectoxford.vision.VisionServiceRestClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class TextRecognitionModule {

    private String key;
    private String rootUrl;

    public TextRecognitionModule(String key, String rootUrl) {
        this.key = key;
        this.rootUrl = rootUrl;
    }

    @Singleton
    @Provides
    VisionServiceRestClient getVisionAPIClient() {
        return new VisionServiceRestClient(key, rootUrl);
    }
}
