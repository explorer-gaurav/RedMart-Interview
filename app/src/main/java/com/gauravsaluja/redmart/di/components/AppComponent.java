package com.gauravsaluja.redmart.di.components;

import android.app.Application;

import com.gauravsaluja.data.api.ProductApi;
import com.gauravsaluja.domain.executor.Executor;
import com.gauravsaluja.domain.executor.PostExecutionThread;
import com.gauravsaluja.domain.repository.ProductRepository;
import com.gauravsaluja.redmart.di.modules.AppModule;
import com.gauravsaluja.redmart.di.modules.NetworkModule;
import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * Created by Gaurav Saluja on 13-Apr-18.
 *
 * Application component
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

    Application providesApplication();

    Executor provideThreadExecutor();

    PostExecutionThread providePostExecutionThread();

    ProductRepository provideProductRepository();

    Cache provideOkHttpCache();

    Gson provideGson();

    OkHttpClient provideOkHttpClient();

    ProductApi provideProductApi();
}