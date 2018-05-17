package com.gauravsaluja.redmart.presenters;

import com.gauravsaluja.domain.model.ProductDetailResponse;
import com.gauravsaluja.redmart.base.BasePresenter;

/**
 * Created by Gaurav Saluja on 20-Apr-18.
 *
 * Contract logic for product detail
 */

public interface DetailsPresenter extends BasePresenter {

    // functions exposed to DetailsFragment
    interface View {
        void onProductDetailLoading();

        void onProductDetailLoaded(ProductDetailResponse detailResponse);

        void onProductDetailFailed();
    }

    // function to bind view and contract
    void setView(View view);

    // load function for getting product detail
    void load(int productId) throws Exception;
}