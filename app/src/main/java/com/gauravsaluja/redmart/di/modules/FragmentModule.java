package com.gauravsaluja.redmart.di.modules;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.gauravsaluja.redmart.di.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Fragment module
 */

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    Fragment providesFragment() {
        return mFragment;
    }

    @Provides
    @PerFragment
    Activity provideActivity() {
        return mFragment.getActivity();
    }
}