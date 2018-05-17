package com.gauravsaluja.redmart.presenters.impl;

import com.gauravsaluja.domain.interactors.GetProductDetailUseCase;
import com.gauravsaluja.domain.interactors.UseCaseObserver;
import com.gauravsaluja.domain.model.ProductDetailResponse;
import com.gauravsaluja.redmart.presenters.DetailsPresenter;

import javax.inject.Inject;

/**
 * Created by Gaurav Saluja on 20-Apr-18.
 *
 * Implementation of product detail contract
 */

public class DetailsPresenterImpl implements DetailsPresenter {

    private GetProductDetailUseCase productDetailUseCase;
    private DetailsPresenter.View presenterView;

    @Inject
    public DetailsPresenterImpl(GetProductDetailUseCase productDetailUseCase) {
        super();
        this.productDetailUseCase = productDetailUseCase;
    }

    @Override
    public void setView(View view) {
        this.presenterView = view;
    }

    @Override
    public void load(int productId) throws Exception {

        // if view is set then process with request
        // else throw exception
        if (presenterView != null) {
            presenterView.onProductDetailLoading();
        } else {
            throw new Exception("setView() not called Before calling load()");
        }

        // dispose existing instance of use case (if any)
        productDetailUseCase.dispose();

        // send the load request
        productDetailUseCase.execute(getProductDetailObserver(), productId);
    }

    // observer for the network call
    private UseCaseObserver<ProductDetailResponse> getProductDetailObserver() {

        return new UseCaseObserver<ProductDetailResponse>() {
            @Override
            public void onNext(ProductDetailResponse productDetailResponse) {
                super.onNext(productDetailResponse);
                presenterView.onProductDetailLoaded(productDetailResponse);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                e.printStackTrace();
                presenterView.onProductDetailFailed();
            }
        };
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
        productDetailUseCase.dispose();
    }
}