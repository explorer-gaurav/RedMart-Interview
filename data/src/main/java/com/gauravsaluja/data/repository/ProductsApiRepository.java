package com.gauravsaluja.data.repository;

import com.gauravsaluja.data.api.ProductApi;
import com.gauravsaluja.domain.model.ProductDetailResponse;
import com.gauravsaluja.domain.model.ProductListingResponse;
import com.gauravsaluja.domain.repository.ProductRepository;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Implementation of repository class defined in 'domain' module
 */

public class ProductsApiRepository implements ProductRepository {

    private ProductApi productApi;

    @Inject
    public ProductsApiRepository(ProductApi productApi) {
        this.productApi = productApi;
    }

    // get products list
    @Override
    public Observable<ProductListingResponse> getProductsList(Map<String, Object> queryParams) {
        return productApi.getProductsListing(queryParams);
    }

    // get product detail
    @Override
    public Observable<ProductDetailResponse> getProductDetail(Integer productId) {
        return productApi.getProductDetails(productId);
    }
}