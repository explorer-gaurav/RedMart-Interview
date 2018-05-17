package com.gauravsaluja.redmart.di.components;

import com.gauravsaluja.redmart.di.modules.ActivityModule;
import com.gauravsaluja.redmart.di.modules.FragmentModule;
import com.gauravsaluja.redmart.di.scope.ConfigPersistent;

import dagger.Component;

/**
 * Created by Gaurav Saluja on 13-Apr-18.
 * <p>
 * Configuration persistent component
 */

@ConfigPersistent
@Component(dependencies = AppComponent.class)
public interface ConfigPersistentComponent {

    ActivityComponent activityComponent(ActivityModule activityModule);

    FragmentComponent fragmentComponent(FragmentModule fragmentModule);
}