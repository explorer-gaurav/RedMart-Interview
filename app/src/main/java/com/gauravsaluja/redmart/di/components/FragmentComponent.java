package com.gauravsaluja.redmart.di.components;

import com.gauravsaluja.redmart.di.modules.FragmentModule;
import com.gauravsaluja.redmart.di.scope.PerFragment;
import com.gauravsaluja.redmart.fragments.DetailsFragment;
import com.gauravsaluja.redmart.fragments.ListingFragment;

import dagger.Subcomponent;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Fragment component
 */

@PerFragment
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(ListingFragment listingFragment);

    void inject(DetailsFragment detailsFragment);
}