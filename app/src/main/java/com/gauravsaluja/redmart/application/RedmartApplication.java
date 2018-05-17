package com.gauravsaluja.redmart.application;

import android.app.Application;

import com.gauravsaluja.domain.utils.Constants;
import com.gauravsaluja.redmart.di.components.AppComponent;
import com.gauravsaluja.redmart.di.components.DaggerAppComponent;
import com.gauravsaluja.redmart.di.modules.AppModule;
import com.gauravsaluja.redmart.di.modules.NetworkModule;

/**
 * Created by Gaurav Saluja on 20-Apr-18.
 *
 * Application class
 */

public class RedmartApplication extends Application {

    private static AppComponent sAppComponent;
    private static final Object sObjectLock = new Object();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    // get app component
    public AppComponent getAppComponent() {
        if (sAppComponent == null)
            synchronized (sObjectLock) {
                if (sAppComponent == null) {
                    sAppComponent = DaggerAppComponent.
                            builder()
                            .appModule(getApplicationModule())
                            .networkModule(getNetworkModule())
                            .build();
                }
            }
        return sAppComponent;
    }

    // get application module
    private AppModule getApplicationModule() {
        return new AppModule(this);
    }

    // get network module
    private NetworkModule getNetworkModule() {
        return new NetworkModule(Constants.BASE_URL);
    }
}