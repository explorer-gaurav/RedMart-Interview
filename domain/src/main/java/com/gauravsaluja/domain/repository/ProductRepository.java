package com.gauravsaluja.domain.repository;

import com.gauravsaluja.domain.model.ProductDetailResponse;
import com.gauravsaluja.domain.model.ProductListingResponse;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Gaurav Saluja on 19-Apr-18.
 *
 * Repository interface
 */

public interface ProductRepository {

    // get products list
    Observable<ProductListingResponse> getProductsList(Map<String, Object> queryParams);

    // get product detail
    Observable<ProductDetailResponse> getProductDetail(Integer productId);
}