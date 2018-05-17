package com.gauravsaluja.domain.executor;

import io.reactivex.Scheduler;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * PostExecutionThread interface, to be used for main / UI threads
 */

public interface PostExecutionThread {

    Scheduler getScheduler();
}