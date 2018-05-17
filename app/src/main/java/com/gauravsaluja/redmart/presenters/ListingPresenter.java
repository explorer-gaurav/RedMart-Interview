package com.gauravsaluja.redmart.presenters;

import com.gauravsaluja.domain.model.ProductListingResponse;
import com.gauravsaluja.redmart.base.BasePresenter;

/**
 * Created by Gaurav Saluja on 20-Apr-18.
 *
 * Contract logic for product listing
 */

public interface ListingPresenter extends BasePresenter {

    // functions exposed to ListingFragment
    interface View {
        void onProductListingLoading();

        void onProductListingLoaded(ProductListingResponse listingResponse);

        void onProductListingFailed();
    }

    // function to bind view and contract
    void setView(View view);

    // load function for getting product listing
    void load(int page) throws Exception;
}