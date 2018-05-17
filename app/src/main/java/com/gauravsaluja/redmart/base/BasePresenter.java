package com.gauravsaluja.redmart.base;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Base class for presenters
 */

public interface BasePresenter {

    void resume();

    void pause();

    void stop();

    void destroy();
}