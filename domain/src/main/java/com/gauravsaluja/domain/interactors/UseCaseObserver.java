package com.gauravsaluja.domain.interactors;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Base class for use case observer
 */

public class UseCaseObserver<T> extends DisposableObserver<T> {

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}