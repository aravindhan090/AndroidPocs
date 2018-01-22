package com.miraldemoproject.application;

import android.app.Application;

import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.miraldemoproject.R;
import com.miraldemoproject.di.components.ApplicationComponent;
import com.miraldemoproject.di.components.DaggerApplicationComponent;
import com.miraldemoproject.di.modules.ApplicationModule;
import com.miraldemoproject.di.modules.TextRecognitionModule;

public class MiralDemoApplication extends Application {

    ApplicationComponent mApplicationComponent;
    private VisionServiceRestClient visionServiceRestClient;

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponent();
    }

    /**
     * Initialize DaggerAplicationComponent with the module which need at application level
     */
    private void initializeApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule("https://api.learn2crack.com/", this)).
                        textRecognitionModule(
                                new TextRecognitionModule(getString(R.string.subscription_key),
                                        "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0"))
                .build();
        visionServiceRestClient = mApplicationComponent.exposeVisionServiceRestClient();
    }

    public VisionServiceRestClient getVisionServiceRestClient() {
        return visionServiceRestClient;
    }
}
