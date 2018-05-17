package com.gauravsaluja.redmart.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.gauravsaluja.redmart.application.RedmartApplication;
import com.gauravsaluja.redmart.di.components.ConfigPersistentComponent;
import com.gauravsaluja.redmart.di.components.DaggerConfigPersistentComponent;
import com.gauravsaluja.redmart.di.components.FragmentComponent;
import com.gauravsaluja.redmart.di.modules.FragmentModule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Base class for fragments
 */

public abstract class BaseFragment extends Fragment {

    private static final String KEY_FRAGMENT_ID = "KEY_FRAGMENT_ID";
    private static final Map<Long, ConfigPersistentComponent> sComponentsMap = new HashMap<>();
    private static final AtomicLong NEXT_ID = new AtomicLong(0);

    private FragmentComponent mFragmentComponent;
    private long mFragmentId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // logic to retain components on orientation change
        mFragmentId = savedInstanceState != null ? savedInstanceState.getLong(KEY_FRAGMENT_ID) : NEXT_ID.getAndIncrement();
        ConfigPersistentComponent configPersistentComponent;
        if (!sComponentsMap.containsKey(mFragmentId)) {
            configPersistentComponent = DaggerConfigPersistentComponent.builder()
                    .appComponent(((RedmartApplication) getActivity().getApplication()).getAppComponent())
                    .build();
            sComponentsMap.put(mFragmentId, configPersistentComponent);
        } else {
            configPersistentComponent = sComponentsMap.get(mFragmentId);
        }
        mFragmentComponent = configPersistentComponent.fragmentComponent(new FragmentModule(this));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_FRAGMENT_ID, mFragmentId);
    }

    @Override
    public void onDestroy() {
        if (!getActivity().isChangingConfigurations()) {
            sComponentsMap.remove(mFragmentId);
        }
        super.onDestroy();
    }

    // get fragment component
    public FragmentComponent getFragmentComponent() {
        return mFragmentComponent;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        List<Fragment> childFragments = getChildFragmentManager().getFragments();
        if (childFragments != null) {
            for (Fragment childFragment : childFragments) {
                if (childFragment != null)
                    childFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }
}