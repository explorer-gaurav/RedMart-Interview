package com.gauravsaluja.redmart.di.modules;

import android.app.Application;
import android.content.Context;

import com.gauravsaluja.data.repository.ProductsApiRepository;
import com.gauravsaluja.domain.executor.Executor;
import com.gauravsaluja.domain.executor.PostExecutionThread;
import com.gauravsaluja.domain.executor.impl.ThreadExecutor;
import com.gauravsaluja.domain.repository.ProductRepository;
import com.gauravsaluja.redmart.threading.MainThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Application module
 */

@Module
public class AppModule {

    Application application;

    public AppModule(Application app) {
        application = app;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    Context provideContext(Application application) {
        return application.getApplicationContext();
    }

    @Provides
    @Singleton
    Executor provideThreadExecutor(ThreadExecutor threadExecutor) {
        return threadExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(MainThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    ProductRepository provideProductRepository(ProductsApiRepository productsApiRepository) {
        return productsApiRepository;
    }
}