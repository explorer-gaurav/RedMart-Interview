package com.gauravsaluja.redmart.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.gauravsaluja.redmart.application.RedmartApplication;
import com.gauravsaluja.redmart.di.components.ActivityComponent;
import com.gauravsaluja.redmart.di.components.ConfigPersistentComponent;
import com.gauravsaluja.redmart.di.components.DaggerConfigPersistentComponent;
import com.gauravsaluja.redmart.di.modules.ActivityModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Base class for activities
 */

public abstract class BaseActivity extends AppCompatActivity {

    private static final String KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID";
    private static final AtomicLong NEXT_ID = new AtomicLong(0);
    private static final Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();

    private ActivityComponent mActivityComponent;
    private long mActivityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // logic to retain components on orientation change
        mActivityId = savedInstanceState != null ? savedInstanceState.getLong(KEY_ACTIVITY_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (!sComponentsMap.containsKey(mActivityId)) {
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(((RedmartApplication) getApplication()).getAppComponent())
                    .build();
            sComponentsMap.put(mActivityId, configPersistentComponent);
        } else {
            configPersistentComponent = sComponentsMap.get(mActivityId);
        }
        mActivityComponent = configPersistentComponent.activityComponent(new ActivityModule(this));
        mActivityComponent.inject(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_ACTIVITY_ID, mActivityId);
    }

    @Override
    protected void onDestroy() {
        if (!isChangingConfigurations()) {
            sComponentsMap.remove(mActivityId);
        }
        super.onDestroy();
    }

    // get activity component
    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        List<Fragment> childFragments = getSupportFragmentManager().getFragments();
        if (childFragments != null) {
            for (Fragment childFragment : childFragments) {
                if (childFragment != null)
                    childFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}