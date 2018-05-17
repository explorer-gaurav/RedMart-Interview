package com.gauravsaluja.redmart.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.gauravsaluja.domain.model.Image;
import com.gauravsaluja.redmart.fragments.PagerImageFragment;

import java.util.List;

/**
 * Created by Gaurav Saluja on 21-Apr-18.
 *
 * Adapter for product image view pager
 */

public class ProductImageAdapter extends FragmentStatePagerAdapter {

    private List<Image> images;
    private static SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public ProductImageAdapter(FragmentManager fm, List<Image> images) {
        super(fm);
        this.images = images;
    }

    @Override
    public Fragment getItem(int position) {
        return PagerImageFragment.newInstance(images.get(position));
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }
}