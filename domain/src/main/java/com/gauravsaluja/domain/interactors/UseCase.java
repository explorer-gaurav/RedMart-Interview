package com.gauravsaluja.domain.interactors;

import com.gauravsaluja.domain.executor.PostExecutionThread;

import java.util.concurrent.Executor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Base class for use case
 */

public abstract class UseCase<T, Params> {

    protected Executor mThreadExecutor;
    protected PostExecutionThread mPostExecutionThread;
    private CompositeDisposable disposables;
    private boolean lastCompositeDisposed = true;

    public UseCase(Executor threadExecutor, PostExecutionThread postExecutionThread) {
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    public abstract Observable<T> buildUseCaseObservable(Params params);

    // main executor function to take the observable and request parameters
    // background threads are taken from mThreadExecutor
    // main thread is taken from mPostExecutionThread
    public void execute(DisposableObserver<T> disposableObserver, Params params) {
        this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribe(disposableObserver);

        addDisposable(disposableObserver);
    }

    // dispose function to be called on onDestroy
    public void dispose() {
        lastCompositeDisposed = true;
        if (!disposables.isDisposed()) {
            disposables.dispose();
            disposables = new CompositeDisposable();
        }
    }

    public boolean isDisposed() {
        return lastCompositeDisposed;
    }

    protected void addDisposable(Disposable disposable) {
        lastCompositeDisposed = false;
        disposables.add(disposable);
    }
}