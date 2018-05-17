package com.gauravsaluja.redmart.di.modules;

import android.app.Activity;
import android.content.Context;

import com.gauravsaluja.redmart.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Activity module
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @PerActivity
    Context providesContext() {
        return mActivity;
    }
}