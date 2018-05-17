package com.gauravsaluja.redmart.presenters.impl;

import com.gauravsaluja.domain.interactors.GetProductsListUseCase;
import com.gauravsaluja.domain.interactors.UseCaseObserver;
import com.gauravsaluja.domain.model.ProductListingResponse;
import com.gauravsaluja.redmart.presenters.ListingPresenter;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by Gaurav Saluja on 20-Apr-18.
 *
 * Implementation of product listing contract
 */

public class ListingPresenterImpl implements ListingPresenter {

    private GetProductsListUseCase productsListUseCase;
    private ListingPresenter.View presenterView;

    @Inject
    public ListingPresenterImpl(GetProductsListUseCase productsListUseCase) {
        super();
        this.productsListUseCase = productsListUseCase;
    }

    @Override
    public void setView(View view) {
        this.presenterView = view;
    }

    @Override
    public void load(int page) throws Exception {

        // if view is set then process with request
        // else throw exception
        if (presenterView != null) {
            presenterView.onProductListingLoading();
        } else {
            throw new Exception("setView() not called Before calling load()");
        }

        // dispose existing instance of use case (if any)
        productsListUseCase.dispose();

        // send the load request
        productsListUseCase.execute(getProductListingObserver(), getQueryParams(page));
    }

    // observer for the network call
    private UseCaseObserver<ProductListingResponse> getProductListingObserver() {

        return new UseCaseObserver<ProductListingResponse>() {
            @Override
            public void onNext(ProductListingResponse productListingResponse) {
                super.onNext(productListingResponse);
                presenterView.onProductListingLoaded(productListingResponse);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                e.printStackTrace();
                presenterView.onProductListingFailed();
            }
        };
    }

    // request query param generator
    private Map<String, Object> getQueryParams(int page) {
        HashMap<String, Object> queryParams = new HashMap<>();
        queryParams.put("page", page);
        return queryParams;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {
        presenterView = null;
        productsListUseCase.dispose();
    }
}