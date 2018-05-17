package com.gauravsaluja.redmart.threading;

import android.os.Handler;
import android.os.Looper;

import com.gauravsaluja.domain.executor.PostExecutionThread;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Implementation of post execution thread which will provide main / UI thread
 */

@Singleton
public class MainThread implements PostExecutionThread {

    private Handler mHandler;

    @Inject
    public MainThread() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}