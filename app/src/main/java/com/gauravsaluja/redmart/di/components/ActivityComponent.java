package com.gauravsaluja.redmart.di.components;

import com.gauravsaluja.redmart.base.BaseActivity;
import com.gauravsaluja.redmart.di.modules.ActivityModule;
import com.gauravsaluja.redmart.di.scope.PerActivity;

import dagger.Subcomponent;

/**
 * Created by Gaurav Saluja on 13-Apr-18.
 *
 * Activity component
 */

@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(BaseActivity baseActivity);
}